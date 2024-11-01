package com.enma.pawfriends.reportemascotas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.launch

@Composable
fun PetReportsScreen(navController: NavController, repository: PetReportRepository) {
    var petReports by remember { mutableStateOf<List<PetReport>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val db = FirebaseFirestore.getInstance()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            try {
                petReports = repository.getPetReports()
            } catch (e: Exception) {
                errorMessage = "Error al cargar los reportes. Por favor, inténtalo de nuevo."
            } finally {
                isLoading = false
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            errorMessage != null -> {
                Text(text = errorMessage!!, color = Color.Red, modifier = Modifier.align(Alignment.Center))
            }
            else -> {
                LazyColumn(modifier = Modifier.padding(16.dp)) {
                    items(petReports) { report ->
                        PetReportItemCard(report = report, onFoundClick = {
                            coroutineScope.launch {
                                try {
                                    FirebaseMessaging.getInstance().subscribeToTopic("pet_${report.id}")
                                        .addOnCompleteListener { task ->
                                            if (task.isSuccessful) {
                                                val notificationTitle = "Mascota Encontrada"
                                                val notificationMessage = "La mascota ${report.name} ha sido encontrada."

                                                val data = hashMapOf(
                                                    "to" to "/topics/pet_${report.id}",
                                                    "notification" to hashMapOf(
                                                        "title" to notificationTitle,
                                                        "body" to notificationMessage
                                                    )
                                                )
                                                db.collection("notifications").add(data)
                                            }
                                        }
                                } catch (e: Exception) {
                                    // Manejar error al enviar la notificación
                                }
                            }
                        })
                    }
                }
            }
        }
    }
}

@Composable
fun PetReportItemCard(report: PetReport, onFoundClick: () -> Unit) {
    val db = FirebaseFirestore.getInstance()
    var userEmail by remember { mutableStateOf("Cargando...") }

    LaunchedEffect(report.ownerId) {
        try {
            if (report.ownerId.isNotEmpty()) {
                val document = db.collection("users").document(report.ownerId).get().await()
                userEmail = document.getString("email") ?: "Desconocido"
            } else {
                userEmail = "Desconocido"
            }
        } catch (e: Exception) {
            userEmail = "Desconocido"
        }
    }

    Column(modifier = Modifier.padding(8.dp)) {
        Text("Reportado por: $userEmail")
        Text("Nombre de la mascota: ${report.name}")
        Text("Tipo: ${report.type}")
        Text("Descripción: ${report.description}")
        Text("Ubicación: ${report.location}")
        Text("Última fecha vista: ${report.date}")
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { onFoundClick() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00C853))
        ) {
            Text("Mascota encontrada", color = Color.White)
        }
    }
}
