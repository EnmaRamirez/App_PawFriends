package com.enma.pawfriends.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.enma.pawfriends.ReporteMascotas.PetReportRepository
import com.enma.pawfriends.ReporteMascotas.PetReportViewModel
import com.enma.pawfriends.ReporteMascotas.PetReportsScreen
import com.enma.pawfriends.ReporteMascotas.ReportPetScreen
import com.enma.pawfriends.view.login.BlanckView
import com.enma.pawfriends.view.login.RegisterPetScreen
import com.enma.pawfriends.view.login.TabsView
import com.enma.pawfriends.view.notas.HomeView
import com.enma.pawfriends.viewmodel.LoginViewModel
import com.enma.pawfriends.viewmodel.NotesViewModel
import androidx.navigation.NavController

@Composable
fun NavManager(loginViewModel: LoginViewModel,
               notesViewModel: NotesViewModel){
    val navController = rememberNavController()

    val petReportRepository = PetReportRepository()

    NavHost(navController = navController, startDestination = "black"){
        composable("black") {
            BlanckView(navController = navController)
        }
        composable("login") {
            TabsView(navController = navController, loginViewModel = loginViewModel)
        }
        composable("home") {
            HomeView(navController = navController, viewModel = notesViewModel)
        }
        composable("register_pet"){
            RegisterPetScreen(navController = navController)
        }
        composable("pet_reports") { // Ruta para la pantalla de reporte de mascotas
            ReportPetScreen(
                onReportSubmitted = { /* Acción a realizar después de reportar */ },
                repository = petReportRepository,
                onViewReports = { navController.navigate("petReports") } // Navega a la pantalla de reportes
            )
        }
        composable("petReports") {
            PetReportsScreen(navController = navController, repository = petReportRepository)
        }


    }
}












