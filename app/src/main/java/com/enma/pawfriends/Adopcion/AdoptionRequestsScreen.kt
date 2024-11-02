package com.enma.pawfriends.Adopcion

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.enma.pawfriends.model.AdoptionRequest
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun AdoptionRequestsScreen(navController: NavController) {
    val context = LocalContext.current
    var adoptionRequests by remember { mutableStateOf<List<AdoptionRequest>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf("") }

    // Cargar todas las solicitudes de adopción de la base de datos
    LaunchedEffect(Unit) {
        Log.d("AdoptionRequestsScreen", "Iniciando carga de solicitudes de adopción")
        val db = FirebaseFirestore.getInstance()
        db.collection("adoptionRequests")
            .get()
            .addOnSuccessListener { result ->
                Log.d("AdoptionRequestsScreen", "Datos obtenidos de Firestore, procesando resultados")
                adoptionRequests = result.documents.mapNotNull { document ->
                    Log.d("AdoptionRequestsScreen", "Documento obtenido: ${document.data}")
                    document.toObject(AdoptionRequest::class.java)
                }
                isLoading = false
                Log.d("AdoptionRequestsScreen", "Solicitudes cargadas: ${adoptionRequests.size}")
            }
            .addOnFailureListener { exception ->
                errorMessage = "Error al cargar las solicitudes de adopción."
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                Log.e("AdoptionRequestsScreen", "Error al cargar las solicitudes", exception)
                isLoading = false
            }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        when {
            isLoading -> {
                Text("Cargando solicitudes...")
                Log.d("AdoptionRequestsScreen", "Cargando solicitudes...")
            }
            errorMessage.isNotEmpty() -> {
                Text(errorMessage)
                Log.d("AdoptionRequestsScreen", "Error: $errorMessage")
            }
            adoptionRequests.isNotEmpty() -> {
                adoptionRequests.forEach { request ->
                    AdoptionRequestItem(
                        request = request,
                        onClick = {
                            navController.navigate("adoption_details/${request.requestId}")
                            Log.d("AdoptionRequestsScreen", "Navegando a detalles de solicitud con ID: ${request.requestId}")
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
            else -> {
                Text("No se encontraron solicitudes de adopción.")
                Log.d("AdoptionRequestsScreen", "No se encontraron solicitudes de adopción")
            }
        }
    }
}

@Composable
fun AdoptionRequestItem(request: AdoptionRequest, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Pet ID: ${request.petId}")
            Text("Requester ID: ${request.requesterId}")
            Text("Status: ${request.status}")
        }
    }
}
