package com.enma.pawfriends.view.login

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.enma.pawfriends.R
import com.enma.pawfriends.model.Pet
import com.enma.pawfriends.services.FirestoreService
import com.google.firebase.storage.FirebaseStorage
import java.util.*

@Composable
fun RegisterPetScreen(navController: NavController) {
    var petName by remember { mutableStateOf("") }
    var petBreed by remember { mutableStateOf("") }
    var petAge by remember { mutableStateOf("") }
    var petHealth by remember { mutableStateOf("") }
    var petCarnet by remember { mutableStateOf("") }
    var photos by remember { mutableStateOf(mutableListOf<String>()) }
    var videos by remember { mutableStateOf(mutableListOf<String>()) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var imageUrl by remember { mutableStateOf("") } // Variable para almacenar la URL de la imagen
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var successMessage by remember { mutableStateOf<String?>(null) }

    val firestoreService = FirestoreService()
    //val context = LocalContext.current
    val storage = FirebaseStorage.getInstance()

    // Launcher para seleccionar imágenes/videos
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
        uri?.let {
            uploadFileToFirebase(it, storage) { url ->
                photos.add(url)
                imageUrl = url // Actualiza la URL de la imagen después de subir
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.servicios),
            contentDescription = "Pet Icon",
            modifier = Modifier.size(80.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Registrar Mascota",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = petName,
            onValueChange = { petName = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = petBreed,
            onValueChange = { petBreed = it },
            label = { Text("Raza") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = petAge,
            onValueChange = { petAge = it },
            label = { Text("Edad") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = petHealth,
            onValueChange = { petHealth = it },
            label = { Text("Salud") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = petCarnet,
            onValueChange = { petCarnet = it },
            label = { Text("Carnet") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Botón para seleccionar imágenes o videos
        Button(
            onClick = { launcher.launch("image/*") }, // Cambia a "video/*" para seleccionar videos
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Seleccionar Foto/Video")
        }

        selectedImageUri?.let {
            Image(
                painter = rememberImagePainter(it),
                contentDescription = "Selected Image",
                modifier = Modifier
                    .size(100.dp)
                    .padding(8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val pet = Pet(
                    name = petName,
                    breed = petBreed,
                    age = petAge.toIntOrNull() ?: 0,
                    health = petHealth,
                    carnet = petCarnet.toIntOrNull() ?: 0,
                    ownerId = "someOwnerId", // Reemplaza con el ID del usuario
                    photos = photos,
                    videos = videos,
                    imageUrl = imageUrl // Asegúrate de pasar el imageUrl aquí
                )
                firestoreService.savePet(
                    pet,
                    onSuccess = {
                        successMessage = "Mascota registrada con éxito"
                        navController.popBackStack()
                    },
                    onFailure = { error -> errorMessage = error }
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
        ) {
            Text("Registrar Mascota", fontSize = 18.sp, color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        errorMessage?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(8.dp)
            )
        }
        successMessage?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

private fun uploadFileToFirebase(
    uri: Uri,
    storage: FirebaseStorage,
    onUrlGenerated: (String) -> Unit
) {
    val storageRef = storage.reference.child("pet_media/${UUID.randomUUID()}")
    storageRef.putFile(uri)
        .addOnSuccessListener {
            storageRef.downloadUrl.addOnSuccessListener { url ->
                onUrlGenerated(url.toString())
            }
        }
        .addOnFailureListener {
            // Manejo de errores
        }
}
