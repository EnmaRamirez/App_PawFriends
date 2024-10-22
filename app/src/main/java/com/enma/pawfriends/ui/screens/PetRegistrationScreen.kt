package com.enma.pawfriends.ui.screens


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.enma.pawfriends.model.Pet
import com.enma.pawfriends.services.FirestoreService
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp

@Composable
fun PetRegistrationScreen (firestoreService: FirestoreService, ownerId: String){
    var petName by remember { mutableStateOf("") }
    var petBreed by remember { mutableStateOf("") }
    var petAge by remember { mutableStateOf(0) }
    var pethealth by remember { mutableStateOf("") }
    var petcarnet by remember { mutableStateOf(0) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)){
        TextField(
            value = petName,
            onValueChange = { petName = it },
            label = { Text("Nombre de mascota") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = petBreed,
            onValueChange = {petBreed = it },
            label = { Text("Raza de mascota") },
            modifier = Modifier.fillMaxWidth()

        )
        TextField(
            value = petAge.toString(),
            onValueChange = { petAge = it.toIntOrNull() ?: 0},
            label = { Text("Edad ") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = pethealth,
            onValueChange = { pethealth = it },
            label = { Text("Salud ") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = petcarnet.toString(),
            onValueChange = { petcarnet = it.toIntOrNull() ?: 0},
            label = { Text("Carnet") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                val pet = Pet(name = petName, breed = petBreed, age = petAge, health = pethealth, carnet = petcarnet, ownerId = ownerId)
                firestoreService.savePet(pet, onSuccess = {

                }, onFailure = { errorMessage = it.message })
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Register Pet")
        }
        errorMessage?.let{
            Spacer(modifier = Modifier.height(8.dp))
            Text(it, color = MaterialTheme.colorScheme.error)
        }
    }
    
}