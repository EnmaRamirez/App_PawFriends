package com.enma.pawfriends.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.enma.pawfriends.reportemascotas.PetReportRepository
import com.enma.pawfriends.reportemascotas.PetReportsScreen
import com.enma.pawfriends.reportemascotas.ReportPetScreen
import com.enma.pawfriends.view.login.BlanckView
import com.enma.pawfriends.view.login.TabsView
import com.enma.pawfriends.view.notas.HomeView
import com.enma.pawfriends.viewmodel.LoginViewModel
import com.enma.pawfriends.viewmodel.NotesViewModel
import com.enma.pawfriends.Elementos
import com.enma.pawfriends.MenuInferior.ConsejosScreen
import com.enma.pawfriends.MenuInferior.InicioScreen
import com.enma.pawfriends.MenuInferior.MensajeriaScreen
import com.enma.pawfriends.MenuInferior.ServiciosScreen
import com.enma.pawfriends.RegisgtroMascotas.MascotasScreen
import com.enma.pawfriends.petregister.RegisterPetScreen
import com.enma.pawfriends.reportemascotas.cosejosdecuidado.AnimalesDomesticos
import com.enma.pawfriends.reportemascotas.cosejosdecuidado.AnimalesGranja
import com.enma.pawfriends.reportemascotas.cosejosdecuidado.CategoriasDeAnimales
import com.enma.pawfriends.reportemascotas.cosejosdecuidado.ListaClinicasVeterinarias
import com.enma.pawfriends.reportemascotas.cosejosdecuidado.PantallaInicial
import com.enma.pawfriends.services.FirestoreService

@Composable
fun NavManager(loginViewModel: LoginViewModel, notesViewModel: NotesViewModel) {
    val navController = rememberNavController()
    val petReportRepository = PetReportRepository()

    NavHost(navController = navController, startDestination = "elementos") {
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
        composable("register_pet") {
            RegisterPetScreen(firestoreService = FirestoreService()) // Asegúrate de inicializar el servicio aquí
        }
        composable("pet_reports") {
            ReportPetScreen(
                onReportSubmitted = { /* Acción a realizar después de reportar */ },
                repository = petReportRepository,
                onViewReports = { navController.navigate("petReports") }
            )
        }
        composable("petReports") {
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
        composable("mascotas") { // Nueva ruta para la pantalla de Mascotas
            MascotasScreen(firestoreService = FirestoreService()) // Inicializa FirestoreService aquí
        }

    }
}

