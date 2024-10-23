package com.enma.pawfriends

import androidx.compose.runtime.Composable
import androidx.navigation.NavController

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.enma.pawfriends.Destinos.*
import com.enma.pawfriends.services.FirestoreService
import com.enma.pawfriends.ui.screens.PetRegistrationScreen

@Composable
fun NavigationHost(navController: NavController, firestoreService: FirestoreService, ownerId: String) {
    NavHost(navController = navController, startDestination = Pantalla1.ruta) {
        composable(Pantalla1.ruta) {
            PetRegistrationScreen(firestoreService = firestoreService, ownerId = ownerId)
        }

    }
}






