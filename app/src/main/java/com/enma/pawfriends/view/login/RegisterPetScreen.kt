package com.enma.pawfriends.view.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.enma.pawfriends.model.Pet

@Composable
fun RegisterPetScreen(navController: NavController) {
    var petName by remember { mutableStateOf("") }
    var petBreed by remember { mutableStateOf("") }
    var petAge by remember { mutableStateOf("") }
    var petHealth by remember { mutableStateOf("") }
    var petCarnet by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var successMessage by remember { mutableStateOf<String?>(null) }

    val firestoreService = FirestoreService()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Registrar Mascota", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        // Nombre de la mascota
        TextField(
            value = petName,
            onValueChange = { petName = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Raza de la mascota
        TextField(
            value = petBreed,
            onValueChange = { petBreed = it },
            label = { Text("Raza") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Edad de la mascota (como texto para permitir edición)
        TextField(
            value = petAge,
            onValueChange = { petAge = it },
            label = { Text("Edad") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Salud de la mascota
        TextField(
            value = petHealth,
            onValueChange = { petHealth = it },
            label = { Text("Salud") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Número de carnet (como texto para permitir edición)
        TextField(
            value = petCarnet,
            onValueChange = { petCarnet = it },
            label = { Text("Carnet") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val age = petAge.toIntOrNull()
                val carnet = petCarnet.toIntOrNull()

                // Validación de entradas
                if (petName.isBlank() || petBreed.isBlank() || petAge.isBlank() || petHealth.isBlank() || petCarnet.isBlank()) {
                    errorMessage = "Todos los campos son obligatorios."
                    return@Button
                }
                if (age == null || carnet == null) {
                    errorMessage = "Edad y Carnet deben ser números válidos."
                    return@Button
                }

                val pet = Pet(name = petName, breed = petBreed, age = age, health = petHealth, carnet = carnet, ownerId = "someOwnerId")
                firestoreService.savePet(pet, onSuccess = {
                    successMessage = "Mascota registrada con éxito"
                    errorMessage = null
                    navController.popBackStack() // Regresar a la pantalla anterior
                }, onFailure = { exception ->
                    errorMessage = exception.message
                    successMessage = null
                })
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrar Mascota")
        }

        // Mostrar mensaje de error
        errorMessage?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(it, color = MaterialTheme.colorScheme.error)
        }

        // Mostrar mensaje de éxito
        successMessage?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(it, color = MaterialTheme.colorScheme.primary)
        }
    }
}
