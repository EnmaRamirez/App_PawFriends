package com.enma.pawfriends.Adopcion

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.enma.pawfriends.model.AdoptionRequest
import com.enma.pawfriends.model.PetAd
import com.google.firebase.firestore.FirebaseFirestore
import java.util.UUID
//Adopcion

@Composable
fun AdoptionListScreen(navController: NavController) {
    // Inicializa Firestore
    val db = FirebaseFirestore.getInstance()
    val petAds = remember { mutableStateListOf<PetAd>() }
    val context = LocalContext.current

    // Cargar los anuncios de adopción
    LaunchedEffect(Unit) {
        fetchPetAds(
            onSuccess = { petAdsList ->
                petAds.clear()
                petAds.addAll(petAdsList)
            },
            onFailure = { exception ->
                Toast.makeText(context, "Error: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
        )
    }

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(petAds) { petAd ->
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .border(BorderStroke(1.dp, Color.Gray))
                    .padding(8.dp)
            ) {
                Text("Pet ID: ${petAd.petId}")
                Text("Description: ${petAd.description}")
                Text("Medical History: ${petAd.medicalHistory}")
                // Aquí puedes agregar una imagen usando las URLs en petAd.photos

                Button(onClick = {
                    // Lógica para solicitar la adopción
                    val adoptionRequest = AdoptionRequest(
                        requestId = UUID.randomUUID().toString(),
                        petId = petAd.petId,
                        requesterId = "currentUserId", // Reemplaza con el ID del usuario actual
                        status = "pending"
                    )
                    submitAdoptionRequest(adoptionRequest,
                        onSuccess = {
                            Toast.makeText(context, "Adoption request submitted", Toast.LENGTH_SHORT).show()
                        },
                        onFailure = { exception ->
                            Toast.makeText(context, "Error: ${exception.message}", Toast.LENGTH_SHORT).show()
                        }
                    )
                }) {
                    Text("Request Adoption")
                }
            }
        }
    }
}

// Implementa las funciones fetchPetAds y submitAdoptionRequest según tus necesidades

fun fetchPetAds(onSuccess: (List<PetAd>) -> Unit, onFailure: (Exception) -> Unit) {
    val db = FirebaseFirestore.getInstance()
    db.collection("petAds")
        .get()
        .addOnSuccessListener { result ->
            val petAds = result.documents.mapNotNull { document ->
                document.toObject(PetAd::class.java)
            }
            onSuccess(petAds)
        }
        .addOnFailureListener { exception ->
            onFailure(exception)
        }
}


fun submitAdoptionRequest(adoptionRequest: AdoptionRequest, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
    val db = FirebaseFirestore.getInstance()
    db.collection("adoptionRequests")
        .add(adoptionRequest)
        .addOnSuccessListener { onSuccess() }
        .addOnFailureListener { exception -> onFailure(exception) }
}

