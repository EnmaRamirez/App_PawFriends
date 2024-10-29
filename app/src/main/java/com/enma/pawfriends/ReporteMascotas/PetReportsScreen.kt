package com.enma.pawfriends.ReporteMascotas

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
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.launch

@Composable
fun PetReportsScreen(repository: PetReportRepository) {
    var petReports by remember { mutableStateOf<List<PetReport>>(emptyList()) } // Lista para almacenar los reportes
    var isLoading by remember { mutableStateOf(true) } // Estado de carga
    var errorMessage by remember { mutableStateOf<String?>(null) } // Estado para mensajes de error
    val db = FirebaseFirestore.getInstance()
    val coroutineScope = rememberCoroutineScope()

    // Lanzamos una corrutina para obtener los datos
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            try {
                petReports = repository.getPetReports() // Llamada al repositorio para obtener los datos
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
                // Mostrar un indicador de carga mientras los datos se obtienen
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            errorMessage != null -> {
                // Mostrar mensaje de error si lo hay
                Text(text = errorMessage!!, color = Color.Red, modifier = Modifier.align(Alignment.Center))
            }
            else -> {
                // Mostrar los datos en una LazyColumn
                LazyColumn(modifier = Modifier.padding(16.dp)) {
                    items(petReports) { report ->
                        PetReportItemCard(report = report, onFoundClick = {
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
    var userName by remember { mutableStateOf("Cargando...") }
    val coroutineScope = rememberCoroutineScope()

    // Utilizando LaunchedEffect para cargar el nombre del usuario desde Firebase
    LaunchedEffect(report.userId) {
        coroutineScope.launch {
            userName = try {
                val document = db.collection("users").document(report.userId).get().await()
                document.getString("name") ?: "Desconocido"
            } catch (e: Exception) {
                "Desconocido"
            }
        }
    }

    Column(modifier = Modifier.padding(8.dp)) {
        Text("Nombre del usuario: $userName") // Mostrar el nombre del usuario
        Text("Nombre de la mascota: ${report.name}")
        Text("Tipo: ${report.type}") // Perdido o Encontrado
        Text("Descripción: ${report.description}")
        Text("Ubicación: ${report.location}")
        Text("Última fecha vista: ${report.date}")
        Spacer(modifier = Modifier.height(8.dp))

        // Botón para marcar la mascota como encontrada
        Button(
            onClick = { onFoundClick() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00C853))
        ) {
            Text("Mascota encontrada", color = Color.White)
        }
    }
}
