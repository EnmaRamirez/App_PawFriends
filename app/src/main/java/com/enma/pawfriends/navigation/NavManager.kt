package com.enma.pawfriends.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.internal.composableLambda
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.enma.pawfriends.ReporteMascotas.PetReportRepository
import com.enma.pawfriends.ReporteMascotas.PetReportViewModel
import com.enma.pawfriends.ReporteMascotas.PetReportsScreen
import com.enma.pawfriends.ReporteMascotas.ReportPetScreen
import com.enma.pawfriends.view.login.BlanckView
import com.enma.pawfriends.view.login.RegisterPetScreen
import com.enma.pawfriends.Elementos
import com.enma.pawfriends.MenuInferior.ConsejosScreen
import com.enma.pawfriends.MenuInferior.InicioScreen
import com.enma.pawfriends.MenuInferior.MensajeriaScreen
import com.enma.pawfriends.MenuInferior.ServiciosScreen
import com.enma.pawfriends.cosejosdecuidado.AnimalesDomesticos
import com.enma.pawfriends.cosejosdecuidado.AnimalesGranja
import com.enma.pawfriends.cosejosdecuidado.CategoriasDeAnimales
import com.enma.pawfriends.cosejosdecuidado.ListaClinicasVeterinarias
import com.enma.pawfriends.cosejosdecuidado.PantallaInicial

@Composable
fun NavManager(loginViewModel: LoginViewModel,
               notesViewModel: NotesViewModel
){
    val navController = rememberNavController()

    val petReportRepository = PetReportRepository()

    NavHost(navController = navController, startDestination = "elementos"){
        composable("elementos") {
            Elementos(navController)
        }
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
        // Pantalla inicial Funcion7
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
        //Barra inferior
        composable("inicio") {
            InicioScreen(navController)
        }
        composable("consejos") {
            ConsejosScreen(navController)
        }
        composable("mensajeria") {
            MensajeriaScreen(navController)
        }
        composable("servicios") {
            ServiciosScreen(navController)
        }


    }
}












