/*package com.enma.pawfriends.RegisgtroMascotas

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.enma.pawfriends.model.Pet
import com.enma.pawfriends.services.FirestoreService // Asegúrate de importar FirestoreService

@Composable
fun MascotasScreen(firestoreService: FirestoreService) { // Cambiado a FirestoreService
    var pets by remember { mutableStateOf<List<Pet>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf("") }

    // Cargar los registros de mascotas
    LaunchedEffect(Unit) {
        firestoreService.getPets(
            onSuccess = { retrievedPets ->
                pets = retrievedPets
                isLoading = false
            },
            onError = { e ->
                errorMessage = "Error al cargar mascotas: ${e.message}"
                isLoading = false
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Mascotas Registradas",
            fontSize = 24.sp,
            style = MaterialTheme.typography.headlineMedium,
            color = Color.Black
        )

        if (isLoading) {
            CircularProgressIndicator()
        } else if (errorMessage.isNotEmpty()) {
            Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
        } else {
            LazyColumn {
                items(pets) { pet ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            // Mostrar el nombre de la mascota
                            Text(text = "Nombre: ${pet.name}")
                            Text(text = "Raza: ${pet.breed}")
                            Text(text = "Edad: ${pet.age}")
                            Text(text = "Salud: ${pet.health}")
                            Text(text = "Carnet: ${pet.carnet}")

                            // Mostrar imagen de la mascota
                            Image(
                                painter = rememberImagePainter(pet.imageUrl),
                                contentDescription = "Imagen de ${pet.name}",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp) // Ajusta la altura de la imagen
                            )

                            // Mostrar el ID del dueño (correo electrónico)
                            Text(text = "Registrado por: ${pet.ownerId}")
                        }
                    }
                }
            }
        }
    }
}*/
