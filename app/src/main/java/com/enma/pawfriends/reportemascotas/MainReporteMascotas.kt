/*package com.enma.pawfriends

-------ESTE ES EL MAIN QUE EJECUTA ESTA VENTANA DE LA APP------

import MainScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import com.enma.pawfriends.reportemascotas.PetReportRepository
import com.enma.pawfriends.ui.theme.PawFriendsTheme

class MainActivity : ComponentActivity() {
    private val repository = PetReportRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PawFriendsTheme {
                Surface {
                    val navController = rememberNavController() // Inicializamos el navController

                    // Llamamos a la función que contiene el NavHost
                    MainScreen(navController = navController, repository = repository)
                }
            }
        }
    }
} */
