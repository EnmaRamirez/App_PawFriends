package com.enma.pawfriends.mensages.chat.ui1
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person

@Composable
fun ChatNavigationBar(navController: NavHostController, uid: String) {
    BottomAppBar(
        modifier = Modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.primary
    ) {
        //Ajusta los iconos y rutas según  pantallas.
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") },
            label = { Text("Inicio") },
            selected = false, // Cambia a true según la pantalla actual
            onClick = { navController.navigate("home") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = "Perfil") },
            label = { Text("Perfil") },
            selected = false, // Cambia a true según la pantalla actual
            onClick = { navController.navigate("profile/$uid") }
        )
    }
}

