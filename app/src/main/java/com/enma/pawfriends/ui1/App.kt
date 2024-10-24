package com.enma.pawfriends.ui1


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun App(navController: NavHostController) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "messaging") {
        composable(route = "messaging") {
            MessagingScreen(navController)
            }
        }
}


