package com.enma.pawfriends.view.login

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.enma.pawfriends.model.Pet
import com.google.firebase.auth.FirebaseAuth

@Composable
fun RegisterPetScreen(navController: NavHostController) {
    val mContext = LocalContext.current

    // Estados para los campos de texto
    var name by remember { mutableStateOf("") }
    var breed by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var health by remember { mutableStateOf("") }
    var carnet by remember { mutableStateOf("") }

    // Estado para las URI de video seleccionados
    var videoUri by remember { mutableStateOf<Uri?>(null) }

    // Mensaje de error
    var errorMessage by remember { mutableStateOf("") }

    // Variables de estado para mostrar etiquetas
    var showNameLabel by remember { mutableStateOf(true) }
    var showBreedLabel by remember { mutableStateOf(true) }
    var showAgeLabel by remember { mutableStateOf(true) }
    var showHealthLabel by remember { mutableStateOf(true) }
    var showCarnetLabel by remember { mutableStateOf(true) }

    // Launcher para seleccionar video desde la galería
    val videoLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        videoUri = uri
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Título de la pantalla
        Text(
            text = "Registro de Mascotas",
            fontSize = 24.sp,
            style = MaterialTheme.typography.headlineMedium
        )

        // Campos de entrada de texto
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
                .padding(vertical = 4.dp)
                .onFocusChanged { focusState ->
                    showNameLabel = if (focusState.isFocused) false else name.isBlank()
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
                .padding(vertical = 4.dp)
                .onFocusChanged { focusState ->
                    showBreedLabel = if (focusState.isFocused) false else breed.isBlank()
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
                .padding(vertical = 4.dp)
                .onFocusChanged { focusState ->
                    showAgeLabel = if (focusState.isFocused) false else age.isBlank()
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
                .padding(vertical = 4.dp)
                .onFocusChanged { focusState ->
                    showHealthLabel = if (focusState.isFocused) false else health.isBlank()
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
                .padding(vertical = 4.dp)
                .onFocusChanged { focusState ->
                    showCarnetLabel = if (focusState.isFocused) false else carnet.isBlank()
                }
        )

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
                if (name.isBlank() || breed.isBlank() || age.isBlank() || health.isBlank() || carnet.isBlank() || videoUri == null) {
                    errorMessage = "Por favor, complete todos los campos y suba un video."
                    return@Button
                }

                // Si la validación pasa, proceder a registrar la mascota
                videoUri?.let { uri ->
                    // Aquí puedes agregar la lógica para manejar el video.
                    // Por ejemplo, subirlo a Firestore o a Firebase Storage.

                    val pet = Pet(
                        name = name,
                        breed = breed,
                        age = age.toIntOrNull() ?: 0,
                        health = health,
                        carnet = carnet.toIntOrNull() ?: 0,
                        ownerId = FirebaseAuth.getInstance().currentUser?.email ?: ""
                        // El videoUrl debe ser asignado aquí después de cargar el video
                    )

                    // Aquí iría la llamada para guardar la mascota en Firestore
                    // firestoreService.savePet(pet, { ... }, { ... })
                    Toast.makeText(mContext, "Mascota registrada con éxito", Toast.LENGTH_LONG).show()
                }
            }) {
                Text("Registrar Mascota")
            }
        }
    }
}
