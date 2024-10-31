package com.enma.pawfriends.reportemascotas.cosejosdecuidado

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "pantalla_inicial") {
        // Pantalla inicial
        composable("pantalla_inicial") {
            PantallaInicial(
                onCuidadoMascotasClick = { navController.navigate("categorias_animales") }, // Navegar a la nueva pantalla de categorías
                onClinicasClick = { navController.navigate("clinicas_veterinarias") }
            )
        }

        // Nueva pantalla de Categorías de Animales
        composable("categorias_animales") {
            CategoriasDeAnimales(
                navController = navController,
                onAnimalDomClick = { navController.navigate("animales_domesticos") },
                onAnimalGranClick = { navController.navigate("animales_granja") }
            )
        }
        // Pantalla de Recomendaciones de Cuidado (Perros, Gatos, Aves, Roedores)
        composable("animales_domesticos") {
            AnimalesDomesticos(navController)
        }
        composable("animales_granja") {
            AnimalesGranja(navController)
        }

        // Pantalla de Clínicas Veterinarias
        composable("clinicas_veterinarias") {
            ListaClinicasVeterinarias()
        }
    }
}