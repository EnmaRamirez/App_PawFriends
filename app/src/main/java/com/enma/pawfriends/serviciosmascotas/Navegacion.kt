package com.enma.pawfriends.serviciosmascotas

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "pantallaInicial") {
        composable("pantallaInicial") {
            PantallaInicial(
                onOfrecerServicioClick = { navController.navigate("offerService") },
                onObtenerServicioClick = { navController.navigate("getService") }
            )
        }
        composable("offerService") {
            //OfferServiceScreen { navController.navigate("getService") }
        }
        composable("getService") {
          //  GetServiceScreen { service ->
               // notifyServiceAccepted(service, "NombreUsuarioActual")
            }
        }
    }
}