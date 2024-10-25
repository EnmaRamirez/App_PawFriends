package com.enma.pawfriends.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.enma.pawfriends.pantallaprincipal.Elementos
import com.enma.pawfriends.pantallaprincipal.PantallaPrincipal
import com.enma.pawfriends.view.login.BlanckView
import com.enma.pawfriends.view.login.TabsView
import com.enma.pawfriends.viewmodel.LoginViewModel
import com.enma.pawfriends.viewmodel.NotesViewModel

@Composable
fun NavManager(loginViewModel: LoginViewModel,
               notesViewModel: NotesViewModel
){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "black"){
        composable("black") {
            BlanckView(navController = navController)
        }
        composable("login") {
            TabsView(navController = navController, loginViewModel = loginViewModel)
        }
        composable("home") {
            Elementos(navController = navController)
        }
        composable("pantalla_principal") {
            PantallaPrincipal(navController = navController)
        }
    }
}