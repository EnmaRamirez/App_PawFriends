package com.enma.pawfriends.mensages.chat.ui1

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.enma.pawfriends.chat.ui1.MessagingScreen

@Composable
fun App(navController: NavController) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "messaging") {
        composable(route = "messaging") {
         //   MessagingScreen(navController)
        }
    }
}
