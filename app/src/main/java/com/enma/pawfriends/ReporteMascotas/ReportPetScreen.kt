package com.enma.pawfriends.reportemascotas

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

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
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var message by remember { mutableStateOf("") } // Estado para el mensaje
    val coroutineScope = rememberCoroutineScope()

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
                fontSize = 15.sp,
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
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(color = Color.Black),
            decorationBox = { innerTextField ->
                if (petName.isEmpty()) {
                    Text("Nombre de la mascota", color = Color.Gray)
                }
                innerTextField()
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Campo para la descripción de la mascota
        BasicTextField(
            value = petDescription,
            onValueChange = { petDescription = it },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(color = Color.Black),
            decorationBox = { innerTextField ->
                if (petDescription.isEmpty()) {
                    Text("Descripción de la mascota", color = Color.Gray)
                }
                innerTextField()
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Campo para la ubicación
        BasicTextField(
            value = petLocation,
            onValueChange = { petLocation = it },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(color = Color.Black),
            decorationBox = { innerTextField ->
                if (petLocation.isEmpty()) {
                    Text("Ubicación", color = Color.Gray)
                }
                innerTextField()
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Campo para la última fecha vista
        BasicTextField(
            value = lastSeenDate,
            onValueChange = { lastSeenDate = it },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(color = Color.Black),
            decorationBox = { innerTextField ->
                if (lastSeenDate.isEmpty()) {
                    Text("Última fecha vista (dd/mm/yyyy)", color = Color.Gray)
                }
                innerTextField()
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Selección de imagen
        ImagePicker(imageUri = imageUri, onImagePicked = { uri -> imageUri = uri })

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para reportar la mascota
        Button(
            onClick = {
                if (petName.isEmpty() || petDescription.isEmpty() || petLocation.isEmpty() || lastSeenDate.isEmpty() || imageUri == null) {
                    message = "Por favor, completa todos los campos e incluye una imagen." // Mensaje de error
                } else {
                    coroutineScope.launch {
                        // Validación y creación de PetReport
                        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                        try {
                            dateFormat.parse(lastSeenDate) // Validar la fecha
                            val petReportId = System.currentTimeMillis().toString()

                            // Subir imagen a Firebase Storage
                            val imageUrl = repository.uploadPetImage(imageUri!!, petReportId)

                            if (imageUrl != null) {
                                val petReport = PetReport(
                                    id = petReportId,
                                    name = petName,
                                    type = petType,
                                    description = petDescription,
                                    location = petLocation,
                                    date = lastSeenDate,
                                    imageUrl = imageUrl // URL de la imagen
                                )
                                repository.reportPet(petReport)
                                message = "Mascota reportada exitosamente." // Mensaje de éxito
                                onReportSubmitted()
                            } else {
                                message = "Error al subir la imagen."
                            }
                        } catch (e: Exception) {
                            message = "Fecha no válida. Usa el formato dd/mm/yyyy."
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA500))
        ) {
            Text("Reportar mascota", color = Color.White)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Botón para limpiar los campos
        Button(
            onClick = {
                petName = ""
                petDescription = ""
                petLocation = ""
                lastSeenDate = ""
                imageUri = null
                message = "" // Limpiar el mensaje al limpiar los campos
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA500))
        ) {
            Text("Limpiar", color = Color.White)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Botón para ver reportes
        Button(
            onClick = {
                onViewReports()
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA500))
        ) {
            Text("Ver reportes", color = Color.White)
        }

        // Mostrar el mensaje
        Spacer(modifier = Modifier.height(8.dp))
        if (message.isNotEmpty()) {
            Text(
                text = message,
                color = if (message.startsWith("Por favor")) Color.Red else Color.Green,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun ImagePicker(
    imageUri: Uri?,
    onImagePicked: (Uri) -> Unit
) {
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let { onImagePicked(it) }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        if (imageUri != null) {
            Image(
                painter = rememberImagePainter(data = imageUri),
                contentDescription = null,
                modifier = Modifier.size(128.dp),
                contentScale = ContentScale.Crop
            )
        } else {
            Text("Selecciona una imagen", color = Color.Gray)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { launcher.launch("image/*") }) {
            Text("Seleccionar imagen")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReportPetScreenPreview() {
    val repository = PetReportRepository() // Simulación de un repositorio
    ReportPetScreen(
        onReportSubmitted = { /* acción de ejemplo */ },
        repository = repository,
        onViewReports = { /* acción de ejemplo */ }
    )
}
