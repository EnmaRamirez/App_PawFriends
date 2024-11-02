package com.enma.pawfriends.navigation


import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.enma.pawfriends.Elementos
import com.enma.pawfriends.ReporteMascotas.PetReportRepository
import com.enma.pawfriends.ReporteMascotas.ReportPetScreen
import com.enma.pawfriends.view.login.BlanckView
import com.enma.pawfriends.view.login.RegisterPetScreen
import com.enma.pawfriends.Elementos
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
import com.enma.pawfriends.chat.ui1.MessagingScreen
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
import com.enma.pawfriends.repuesto.viewmodel.LoginViewModel
import com.enma.pawfriends.repuesto.viewmodel.NotesViewModel
import com.enma.pawfriends.serviciosmascotas.GetServiceScreen
import com.enma.pawfriends.serviciosmascotas.OfferServiceScreen
import com.enma.pawfriends.serviciosmascotas.notifyServiceAccepted

import com.google.firebase.auth.FirebaseAuth

@Composable
fun NavManager(loginViewModel: LoginViewModel, notesViewModel: NotesViewModel) {
    val navController = rememberNavController()
    val petReportRepository = PetReportRepository()
    // Obtén el UID del usuario autenticado en Firebase
    val uid = FirebaseAuth.getInstance().currentUser?.uid

    NavHost(navController = navController, startDestination = "elementos") {
        composable("elementos") {
            Elementos(navController)
        }
        composable("elementos") { Elementos(navController) }
        composable("black") { BlanckView(navController = navController) }
        composable("login") {
            TabsView(
                navController = navController,
                loginViewModel = loginViewModel
            )
        }
        composable("home") {
            HomeView(navController = navController, viewModel = notesViewModel)
        }
        composable("register_pet") {
            RegisterPetScreen(navController = navController)
        }
        composable("home") { HomeView(navController = navController, viewModel = notesViewModel) }
        composable("register_pet") { RegisterPetScreen(navController = navController) }
        composable("pet_reports") {
            ReportPetScreen(
                onReportSubmitted = { /* Acción a realizar después de reportar */ },
                repository = petReportRepository,
                onViewReports = { navController.navigate("pet_reports_list") }
            )
        }
        composable("pet_reports_list") {
            PetReportsScreen(navController = navController, repository = petReportRepository)
                onViewReports = { navController.navigate("petReports") }
            )
        }
        composable("petReports") {
            PetReportsScreen(
                navController = navController,
                repository = petReportRepository
            )
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
        composable("animales_domesticos") { AnimalesDomesticos(navController) }
        composable("animales_granja") { AnimalesGranja(navController) }
        composable("clinicas_veterinarias") { ListaClinicasVeterinarias() }
        composable("inicio") { InicioScreen(navController) }
        composable("consejos") { ConsejosScreen(navController) }
        composable("mensajeria") {
            // Pasa el UID como parámetro a la pantalla de mensajería
            if (uid != null) {
                MessagingScreen(navController = navController, uid = uid)
            } else {


// Aquí puedes manejar el caso en que el usuario no esté autenticado
            }
        }
        composable("pantallaInicial") {
            com.enma.pawfriends.serviciosmascotas.PantallaInicial(
                onOfrecerServicioClick = { navController.navigate("offerService") },
                onObtenerServicioClick = { navController.navigate("getService") }
            )
        }
        composable("offerService") {
            OfferServiceScreen ( navController )
        }
        composable("getService") {
            GetServiceScreen { service ->
                notifyServiceAccepted(service, "NombreUsuarioActual")
            }

        }

    }
}
