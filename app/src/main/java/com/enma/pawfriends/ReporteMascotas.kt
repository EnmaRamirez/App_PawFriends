package com.enma.pawfriends

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import com.enma.pawfriends.ui.theme.PawFriendsTheme
import android.net.Uri

class ReporteMascotas : ComponentActivity() {
    private val viewModel: PetViewModel by viewModels()  // Aquí estás instanciando el ViewModel correctamente

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reporte_mascotas)

        val composeView: ComposeView = findViewById(R.id.compose_view)
        composeView.setContent {
            PawFriendsTheme {
                ReportPetScreen(viewModel)
            }
        }
    }
}

@Composable
fun ReportPetScreen(viewModel: PetViewModel) {
    val petName = remember { mutableStateOf("") }
    val petDescription = remember { mutableStateOf("") }
    val location = remember { mutableStateOf("") }
    val date = remember { mutableStateOf("") }
    val imageUri = remember { mutableStateOf<Uri?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(value = petName.value, onValueChange = { petName.value = it }, label = { Text("Nombre de la mascota") })
        TextField(value = petDescription.value, onValueChange = { petDescription.value = it }, label = { Text("Descripción") })
        TextField(value = location.value, onValueChange = { location.value = it }, label = { Text("Ubicación") })
        TextField(value = date.value, onValueChange = { date.value = it }, label = { Text("Fecha") })
        Button(onClick = { /* Abrir selector de archivos */ }) { Text("Subir imagen") }
        Button(onClick = {
            viewModel.reportPet(petName.value, petDescription.value, location.value, date.value, imageUri.value)
        }) {
            Text("Reportar Mascota")
        }
    }
}
