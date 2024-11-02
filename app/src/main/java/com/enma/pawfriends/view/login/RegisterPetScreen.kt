package com.enma.pawfriends.petregister

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.enma.pawfriends.services.FirestoreService
import com.enma.pawfriends.model.Pet
import com.google.firebase.auth.FirebaseAuth

@Composable
fun RegisterPetScreen(firestoreService: FirestoreService) {
    val mContext = LocalContext.current

    // Estados para los campos de texto
    var name by remember { mutableStateOf("") }
    var breed by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var health by remember { mutableStateOf("") }
    var carnet by remember { mutableStateOf("") }

    // Estado para las URI de la imagen y video seleccionados
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var videoUri by remember { mutableStateOf<Uri?>(null) }

    // Mensaje de error
    var errorMessage by remember { mutableStateOf("") }

    // Variables de estado para mostrar etiquetas
    var showNameLabel by remember { mutableStateOf(true) }
    var showBreedLabel by remember { mutableStateOf(true) }
    var showAgeLabel by remember { mutableStateOf(true) }
    var showHealthLabel by remember { mutableStateOf(true) }
    var showCarnetLabel by remember { mutableStateOf(true) }

    // Launchers para seleccionar imagen y video desde la galería
    val imageLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
    }
    val videoLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        videoUri = uri
    }

    // Recordar el estado de desplazamiento vertical
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),  // Habilitar desplazamiento vertical
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Título de la pantalla
        Text(
            text = "Registro de Mascotas",
            fontSize = 24.sp,
            style = MaterialTheme.typography.headlineMedium
        )

        // Campos de entrada de texto con altura aumentada y tamaño de fuente fijo
        TextField(
            value = name,
            onValueChange = {
                name = it
                showNameLabel = name.isBlank() // Mantener etiqueta visible si el campo está vacío
            },
            label = { if (showNameLabel) Text("Nombre") },
            textStyle = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp),
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .padding(vertical = 4.dp)
                .onFocusChanged { focusState ->
                    if (focusState.isFocused) {
                        showNameLabel = false
                    } else {
                        showNameLabel = name.isBlank()
                    }
                }
        )
        TextField(
            value = breed,
            onValueChange = {
                breed = it
                showBreedLabel = breed.isBlank() // Mantener etiqueta visible si el campo está vacío
            },
            label = { if (showBreedLabel) Text("Raza") },
            textStyle = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp),
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .padding(vertical = 4.dp)
                .onFocusChanged { focusState ->
                    if (focusState.isFocused) {
                        showBreedLabel = false
                    } else {
                        showBreedLabel = breed.isBlank()
                    }
                }
        )
        TextField(
            value = age,
            onValueChange = {
                age = it
                showAgeLabel = age.isBlank() // Mantener etiqueta visible si el campo está vacío
            },
            label = { if (showAgeLabel) Text("Edad") },
            textStyle = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp),
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .padding(vertical = 4.dp)
                .onFocusChanged { focusState ->
                    if (focusState.isFocused) {
                        showAgeLabel = false
                    } else {
                        showAgeLabel = age.isBlank()
                    }
                }
        )
        TextField(
            value = health,
            onValueChange = {
                health = it
                showHealthLabel = health.isBlank() // Mantener etiqueta visible si el campo está vacío
            },
            label = { if (showHealthLabel) Text("Salud") },
            textStyle = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp),
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .padding(vertical = 4.dp)
                .onFocusChanged { focusState ->
                    if (focusState.isFocused) {
                        showHealthLabel = false
                    } else {
                        showHealthLabel = health.isBlank()
                    }
                }
        )
        TextField(
            value = carnet,
            onValueChange = {
                carnet = it
                showCarnetLabel = carnet.isBlank() // Mantener etiqueta visible si el campo está vacío
            },
            label = { if (showCarnetLabel) Text("Carnet de Vacunación") },
            textStyle = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp),
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .padding(vertical = 4.dp)
                .onFocusChanged { focusState ->
                    if (focusState.isFocused) {
                        showCarnetLabel = false
                    } else {
                        showCarnetLabel = carnet.isBlank()
                    }
                }
        )

        // Mostrar imagen seleccionada
        imageUri?.let { uri ->
            Image(
                painter = rememberImagePainter(uri),
                contentDescription = "Selected Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp) // Ajusta la altura de la imagen
            )
        }

        // Mostrar video seleccionado con texto más pequeño
        videoUri?.let { uri ->
            Text(
                text = "Video seleccionado: $uri",
                fontSize = 12.sp // Tamaño de fuente más pequeño
            )
        }

        // Mensaje de error con texto más pequeño
        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                fontSize = 12.sp // Tamaño de fuente más pequeño
            )
        }

        // Fila para colocar los botones horizontalmente
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            // Botón para seleccionar imagen
            OutlinedButton(onClick = { imageLauncher.launch("image/*") }) {
                Text("Subir Imagen")
            }

            // Botón para seleccionar video (opcional)
            OutlinedButton(onClick = { videoLauncher.launch("video/*") }) {
                Text("Subir Video")
            }
        }

        // Fila para los botones de limpiar y registrar, también horizontal
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            // Botón para limpiar los campos
            Button(onClick = {
                name = ""
                breed = ""
                age = ""
                health = ""
                carnet = ""
                imageUri = null
                videoUri = null
                errorMessage = ""
                // Reiniciar etiquetas
                showNameLabel = true
                showBreedLabel = true
                showAgeLabel = true
                showHealthLabel = true
                showCarnetLabel = true
            }) {
                Text("Limpiar Campos")
            }

            // Botón para registrar la mascota
            Button(onClick = {
                // Validar campos
                if (name.isBlank() || breed.isBlank() || age.isBlank() || health.isBlank() || carnet.isBlank() || imageUri == null) {
                    errorMessage = "Por favor, complete todos los campos y suba una imagen."
                    return@Button
                }

                // Si la validación pasa, proceder a registrar la mascota
                imageUri?.let { uri ->
                    firestoreService.uploadMediaFile(uri, { imageUrl ->
                        val pet = Pet(
                            name = name,
                            breed = breed,
                            age = age.toIntOrNull() ?: 0,
                            health = health,
                            carnet = carnet.toIntOrNull() ?: 0,
                            ownerId = FirebaseAuth.getInstance().currentUser?.email ?: "",
                            imageUrl = imageUrl
                        )

                        firestoreService.savePet(pet, {
                            // Acción al registrar la mascota exitosamente
                            Toast.makeText(mContext, "Mascota registrada con éxito", Toast.LENGTH_LONG).show()
                            // Limpiar los campos después de registrar
                            name = ""
                            breed = ""
                            age = ""
                            health = ""
                            carnet = ""
                            imageUri = null
                            videoUri = null
                            errorMessage = ""
                            // Reiniciar etiquetas
                            showNameLabel = true
                            showBreedLabel = true
                            showAgeLabel = true
                            showHealthLabel = true
                            showCarnetLabel = true
                        }, { e ->
                            // Manejo de errores
                            errorMessage = "Error al registrar la mascota."
                        })
                    }, { e ->
                        // Manejo de errores al subir la imagen
                        errorMessage = "Error al subir la imagen."
                    })
                }
            }) {
                Text("Registrar Mascota")
            }
        }
    }
}
