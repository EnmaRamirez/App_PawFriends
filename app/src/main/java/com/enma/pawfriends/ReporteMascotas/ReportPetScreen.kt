package com.enma.pawfriends.ReporteMascotas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ReportPetScreen(
    onReportSubmitted: () -> Unit,
    repository: PetReportRepository,
    onViewReports: () -> Unit
) {
    var petName by remember { mutableStateOf("") }
    var petType by remember { mutableStateOf("Perdido") }
    var petDescription by remember { mutableStateOf("") }
    var petLocation by remember { mutableStateOf("") }
    var lastSeenDate by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") } // Estado para el mensaje
    val coroutineScope = rememberCoroutineScope()
    val auth = FirebaseAuth.getInstance() // Obtener instancia de FirebaseAuth

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Título
        Text(
            text = "REPORTE DE MASCOTAS DESAPARECIDAS",
            style = TextStyle(
                fontSize = 18.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally)
        )

        // Campo para el nombre de la mascota
        BasicTextField(
            value = petName,
            onValueChange = { petName = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .background(Color.LightGray),
            textStyle = TextStyle(color = Color.Black),
            decorationBox = { innerTextField ->
                if (petName.isEmpty()) {
                    Text("Nombre de la mascota", color = Color.Gray)
                }
                innerTextField()
            }
        )

        // Campo para la descripción de la mascota
        BasicTextField(
            value = petDescription,
            onValueChange = { petDescription = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .background(Color.LightGray),
            textStyle = TextStyle(color = Color.Black),
            decorationBox = { innerTextField ->
                if (petDescription.isEmpty()) {
                    Text("Descripción de la mascota", color = Color.Gray)
                }
                innerTextField()
            }
        )

        // Campo para la ubicación
        BasicTextField(
            value = petLocation,
            onValueChange = { petLocation = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .background(Color.LightGray),
            textStyle = TextStyle(color = Color.Black),
            decorationBox = { innerTextField ->
                if (petLocation.isEmpty()) {
                    Text("Ubicación", color = Color.Gray)
                }
                innerTextField()
            }
        )

        // Campo para la última fecha vista
        BasicTextField(
            value = lastSeenDate,
            onValueChange = { lastSeenDate = it.trim() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .background(Color.LightGray),
            textStyle = TextStyle(color = Color.Black),
            decorationBox = { innerTextField ->
                if (lastSeenDate.isEmpty()) {
                    Text("Última fecha vista (dd/mm/yyyy)", color = Color.Gray)
                }
                innerTextField()
            }
        )

        // Botón para reportar la mascota
        Button(
            onClick = {
                if (petName.isEmpty() || petDescription.isEmpty() || petLocation.isEmpty() || lastSeenDate.isEmpty()) {
                    message = "Por favor, completa todos los campos."
                } else {
                    coroutineScope.launch {
                        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                        try {
                            dateFormat.parse(lastSeenDate) // Validar la fecha
                            val userId = auth.currentUser?.uid ?: "usuario_placeholder"
                            val petReport = PetReport(
                                id = System.currentTimeMillis().toString(),
                                name = petName,
                                type = petType,
                                description = petDescription,
                                location = petLocation,
                                date = lastSeenDate,
                                ownerId = userId // Aquí se debe reemplazar con el ID del usuario autenticado
                            )
                            repository.reportPet(petReport)
                            message = "Mascota reportada exitosamente."
                            onReportSubmitted()
                        } catch (e: Exception) {
                            message = "Fecha no válida. Usa el formato dd/mm/yyyy."
                        }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA500))
        ) {
            Text("Reportar mascota", color = Color.White)
        }

        // Botón para ver reportes
        Button(
            onClick = {
                onViewReports()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA500))
        ) {
            Text("Ver reportes", color = Color.White)
        }

        // Mostrar el mensaje
        if (message.isNotEmpty()) {
            Text(
                text = message,
                color = if (message.startsWith("Por favor") || message.startsWith("Fecha")) Color.Red else Color.Green,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}
