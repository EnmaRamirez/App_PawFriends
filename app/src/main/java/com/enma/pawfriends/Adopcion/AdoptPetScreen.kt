package com.enma.pawfriends.Adopcion

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.enma.pawfriends.model.PetAd
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.*

@Composable
fun AdoptPetScreen(navController: NavController) {
    var petId by remember { mutableStateOf("") }
    var ownerId by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var medicalHistory by remember { mutableStateOf("") }
    var photoUri by remember { mutableStateOf<Uri?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val activity = context as? Activity

    // Verificación de permisos para acceder a la galería
    LaunchedEffect(Unit) {
        if (activity != null && ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                1
            )
        }
    }

    // Lanzador para seleccionar una imagen de la galería
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            if (uri != null) {
                photoUri = uri
            } else {
                Toast.makeText(context, "No se seleccionó ninguna imagen", Toast.LENGTH_SHORT).show()
            }
        }
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Ofrecer Mascota en Adopción",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = petId,
                onValueChange = { petId = it },
                label = { Text("Identificación de Mascota") },
                modifier = Modifier.fillMaxWidth(),
            )

            OutlinedTextField(
                value = ownerId,
                onValueChange = { ownerId = it },
                label = { Text("Identificación de Propietario") },
                modifier = Modifier.fillMaxWidth(),
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth(),
            )

            OutlinedTextField(
                value = medicalHistory,
                onValueChange = { medicalHistory = it },
                label = { Text("Historial Médico") },
                modifier = Modifier.fillMaxWidth(),
            )

            Button(
                onClick = { launcher.launch("image/*") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Seleccionar Foto")
            }

            // Mostrar el indicador de carga
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.padding(8.dp))
            }

            Button(
                onClick = {
                    if (photoUri != null) {
                        isLoading = true
                        uploadPhotoToFirebase(context, photoUri!!) { imageUrl ->
                            val petAd = PetAd(petId, ownerId, description, medicalHistory, listOf(imageUrl))
                            addPetAd(
                                petAd,
                                onSuccess = {
                                    isLoading = false
                                    Toast.makeText(context, "Mascota ofrecida en adopción", Toast.LENGTH_SHORT).show()
                                    navController.navigate("adoption_requests") // Navegar a la pantalla de solicitudes
                                },
                                onFailure = { exception ->
                                    isLoading = false
                                    Toast.makeText(context, "Error: ${exception.message}", Toast.LENGTH_SHORT).show()
                                }
                            )
                        }
                    } else {
                        Toast.makeText(context, "Por favor selecciona una foto", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
            ) {
                Text("Ofrecer mascota en adopción", color = MaterialTheme.colorScheme.onPrimary)
            }
        }
    }
}

// Función para subir la foto a Firebase Storage
fun uploadPhotoToFirebase(context: Context, uri: Uri, onUploadSuccess: (String) -> Unit) {
    val storageReference = FirebaseStorage.getInstance().reference
        .child("pet_photos/${UUID.randomUUID()}")

    storageReference.putFile(uri)
        .addOnSuccessListener { taskSnapshot ->
            storageReference.downloadUrl.addOnSuccessListener { downloadUri ->
                onUploadSuccess(downloadUri.toString())
                Toast.makeText(context, "Foto subida exitosamente", Toast.LENGTH_SHORT).show()
            }
        }
        .addOnFailureListener { exception ->
            Toast.makeText(context, "Error al subir la foto: ${exception.message}", Toast.LENGTH_SHORT).show()
        }
}

// Función para añadir datos a Firestore
fun addPetAd(petAd: PetAd, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
    val db = FirebaseFirestore.getInstance()
    db.collection("petAds")
        .add(petAd)
        .addOnSuccessListener { onSuccess() }
        .addOnFailureListener { exception -> onFailure(exception) }
}
