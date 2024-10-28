package com.enma.pawfriends.view.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.enma.pawfriends.model.Pet
import com.enma.pawfriends.services.FirestoreService

@Composable
fun RegisterPetScreen(navController: NavController) {
    var petName by remember { mutableStateOf("") }
    var petBreed by remember { mutableStateOf("") }
    var petAge by remember { mutableStateOf(0) }
    var petHealth by remember { mutableStateOf("") }
    var petCarnet by remember { mutableStateOf(0) }
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

        TextField(
            value = petName,
            onValueChange = { petName = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = petBreed,
            onValueChange = { petBreed = it },
            label = { Text("Raza") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = petAge.toString(),
            onValueChange = { petAge = it.toIntOrNull() ?: 0 },
            label = { Text("Edad") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = petHealth,
            onValueChange = { petHealth = it },
            label = { Text("Salud") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = petCarnet.toString(),
            onValueChange = { petCarnet = it.toIntOrNull() ?: 0 },
            label = { Text("Carnet") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                val pet = Pet(name = petName, breed = petBreed, age = petAge, health = petHealth, carnet = petCarnet, ownerId = "someOwnerId")
                firestoreService.savePet(pet, onSuccess = {
                    successMessage = "Mascota registrada con éxito"
                    // Acciones en caso de éxito
                    navController.popBackStack() // Regresar a la pantalla anterior
                }, onFailure = { errorMessage = it.message })
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrar Mascota")
        }

        errorMessage?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(it, color = MaterialTheme.colorScheme.error)
        }
        successMessage?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(it, color = MaterialTheme.colorScheme.primary)
        }
    }
}