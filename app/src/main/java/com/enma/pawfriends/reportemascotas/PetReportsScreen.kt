import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.enma.pawfriends.reportemascotas.PetReportRepository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.launch

@Composable
fun PetReportsScreen(repository: PetReportRepository) {
    val coroutineScope = rememberCoroutineScope()
    var petReports by remember { mutableStateOf<List<PetReport>>(emptyList()) }
    val db = FirebaseFirestore.getInstance()

    // Lanzamos una corrutina para obtener los datos
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            petReports = repository.getPetReports()
        }
    }

    // Mostrar los datos en una LazyColumn
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(petReports) { report ->
            PetReportItem(report, onFoundClick = {
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

@Composable
fun PetReportItem(report: PetReport, onFoundClick: () -> Unit) {
    Card(modifier = Modifier.padding(8.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Nombre: ${report.name}")
            Text("Tipo: ${report.type}")
            Text("Descripción: ${report.description}")
            Text("Ubicación: ${report.location}")
            Text("Última fecha vista: ${report.date}")
            Text("Reportado por: ${report.userName}") // Mostrar el nombre del usuario
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
}
