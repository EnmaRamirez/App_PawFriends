package com.enma.pawfriends.Adopcion

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.enma.pawfriends.model.AdoptionRequest
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun AdoptionDetailsScreen(navController: NavController, requestId: String) {
    val context = LocalContext.current
    var adoptionRequest by remember { mutableStateOf<AdoptionRequest?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf("") }

    // Cargar la solicitud de adopción de la base de datos según el requestId
    LaunchedEffect(requestId) {
        Log.d("AdoptionDetailsScreen", "Cargando solicitud con ID: $requestId")
        val db = FirebaseFirestore.getInstance()
        db.collection("adoptionRequests").document(requestId)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    adoptionRequest = document.toObject(AdoptionRequest::class.java)
                    Log.d("AdoptionDetailsScreen", "Solicitud cargada: $adoptionRequest")
                    isLoading = false
                } else {
                    errorMessage = "Solicitud no encontrada."
                    isLoading = false
                }
            }
            .addOnFailureListener { exception ->
                Log.e("AdoptionDetailsScreen", "Error al cargar la solicitud", exception)
                errorMessage = "Error al cargar la solicitud."
                isLoading = false
            }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        when {
            isLoading -> Text("Cargando solicitud...")
            errorMessage.isNotEmpty() -> Text(errorMessage)
            adoptionRequest != null -> {
                Text("Request ID: ${adoptionRequest!!.requestId}")
                Text("Pet ID: ${adoptionRequest!!.petId}")
                Text("Requester ID: ${adoptionRequest!!.requesterId}")
                Text("Status: ${adoptionRequest!!.status}")

                Row {
                    ActionButton("Aceptar", "accepted", requestId, context, navController)
                    Spacer(modifier = Modifier.width(8.dp))
                    ActionButton("Rechazar", "rejected", requestId, context, navController)
                }
            }
        }
    }
}

@Composable
fun ActionButton(label: String, status: String, requestId: String, context: Context, navController: NavController) {
    var isProcessing by remember { mutableStateOf(false) }

    Button(
        onClick = {
            isProcessing = true
            updateAdoptionStatus(requestId, status, context) {
                isProcessing = false
                navController.popBackStack() // Regresar a la pantalla anterior después de la actualización
            }
        },
        enabled = !isProcessing,
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Text(if (isProcessing) "Procesando..." else label)
    }
}

// Función para actualizar el estado de una solicitud de adopción en Firestore
fun updateAdoptionStatus(requestId: String, status: String, context: Context, onComplete: () -> Unit) {
    val db = FirebaseFirestore.getInstance()

    db.collection("adoptionRequests")
        .document(requestId)
        .update("status", status)
        .addOnSuccessListener {
            Toast.makeText(context, "Solicitud actualizada a $status", Toast.LENGTH_SHORT).show()
            onComplete()
        }
        .addOnFailureListener { exception ->
            Toast.makeText(context, "Error al actualizar la solicitud: ${exception.message}", Toast.LENGTH_SHORT).show()
            onComplete()
        }
}
