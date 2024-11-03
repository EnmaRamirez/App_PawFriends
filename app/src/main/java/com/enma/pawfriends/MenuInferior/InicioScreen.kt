package com.enma.pawfriends.MenuInferior

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.enma.pawfriends.R
import com.enma.pawfriends.model.Pet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InicioScreen(navController: NavController) {
    // Simulando una lista de mascotas, en un caso real obtendrás esto de Firestore o tu fuente de datos
    val petList = remember {
        listOf(
            Pet(name = "Fido", breed = "Labrador", age = 2, health = "Saludable", carnet = 12345, imageUrl = "url_de_la_imagen1"),
            Pet(name = "Whiskers", breed = "Siames", age = 3, health = "Saludable", carnet = 12346, imageUrl = "url_de_la_imagen2"),
            // Agrega más mascotas según sea necesario
        )
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text("Inicio") },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
        )

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(petList) { pet ->
                PetCard(pet)
            }
        }
    }
}

@Composable
fun PetCard(pet: Pet) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {

            Image(
                painter = painterResource(id = R.drawable.perros),
                contentDescription = pet.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = pet.name, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text(text = "Raza: ${pet.breed}", fontSize = 16.sp)
            Text(text = "Edad: ${pet.age} años", fontSize = 16.sp)
            Text(text = "Salud: ${pet.health}", fontSize = 16.sp)
        }
    }
}

