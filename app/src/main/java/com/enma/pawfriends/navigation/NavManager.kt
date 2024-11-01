package com.enma.pawfriends.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.enma.pawfriends.view.login.RegisterPetScreen
import com.enma.pawfriends.view.login.TabsView
import com.enma.pawfriends.view.notas.HomeView
import com.enma.pawfriends.viewmodel.LoginViewModel
import com.enma.pawfriends.viewmodel.NotesViewModel
import com.enma.pawfriends.MenuInferior.ConsejosScreen
import com.enma.pawfriends.MenuInferior.InicioScreen
import com.enma.pawfriends.MenuInferior.MensajeriaScreen
import com.enma.pawfriends.MenuInferior.ServiciosScreen
import com.enma.pawfriends.ReporteMascotas.PetReportRepository
import com.enma.pawfriends.ReporteMascotas.ReportPetScreen
import com.enma.pawfriends.ReporteMascotas.PetReportsScreen
import com.enma.pawfriends.cosejosdecuidado.AnimalesDomesticos
import com.enma.pawfriends.cosejosdecuidado.AnimalesGranja
import com.enma.pawfriends.cosejosdecuidado.CategoriasDeAnimales
import com.enma.pawfriends.cosejosdecuidado.ListaClinicasVeterinarias
import com.enma.pawfriends.cosejosdecuidado.PantallaInicial
import com.enma.pawfriends.pantallaprincipal.Elementos
import com.enma.pawfriends.view.LeaderboardScreen
import com.enma.pawfriends.view.UserProfileScreen
import com.enma.pawfriends.view.reviews.ReviewScreen
import com.enma.pawfriends.viewmodel.ReviewViewModel

@Composable
fun NavManager(
    loginViewModel: LoginViewModel,
    notesViewModel: NotesViewModel
) {
    val navController = rememberNavController()

    // Crear PetReportRepository solo una vez
    val petReportRepository = PetReportRepository()

    NavHost(navController = navController, startDestination = "elementos") {
        composable("elementos") {
            Elementos(navController)
        }
        composable("login") {
            TabsView(navController = navController, loginViewModel = loginViewModel)
        }
        composable("home") {
            HomeView(navController = navController, viewModel = notesViewModel)
        }
        composable("register_pet") {
            RegisterPetScreen(navController = navController)
        }
        composable("pet_reports") {
            ReportPetScreen(
                onReportSubmitted = { /* Acción a realizar después de reportar */ },
                repository = petReportRepository,
                onViewReports = { navController.navigate("pet_reports_list") }
            )
        }
        composable("pet_reports_list") {
            PetReportsScreen(navController = navController, repository = petReportRepository)
        }
        composable("pantalla_inicial") {
            PantallaInicial(
                onCuidadoMascotasClick = { navController.navigate("categorias_animales") },
                onClinicasClick = { navController.navigate("clinicas_veterinarias") }
            )
        }
        composable("categorias_animales") {
            CategoriasDeAnimales(
                navController = navController,
                onAnimalDomClick = { navController.navigate("animales_domesticos") },
                onAnimalGranClick = { navController.navigate("animales_granja") }
            )
        }
        composable("review_screen") {
            val reviewViewModel: ReviewViewModel = viewModel() // Usando `viewModel()`
            ReviewScreen(
                navController = navController,
                onReviewSubmit = { review ->
                    reviewViewModel.addReview(review)
                    navController.popBackStack()
                }
            )
        }
        composable("animales_domesticos") {
            AnimalesDomesticos(navController)
        }
        composable("animales_granja") {
            AnimalesGranja(navController)
        }
        composable("clinicas_veterinarias") {
            ListaClinicasVeterinarias()
        }
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
        composable("userProfile") {
            UserProfileScreen(navController = navController)
        }
        composable("leaderboard") {
            LeaderboardScreen(navController = navController)
        }
    }
}
