package com.enma.pawfriends.ReporteMascotas

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun MainScreen(navController: NavHostController = rememberNavController(), repository: PetReportRepository) {
    NavHost(
        navController = navController,
        startDestination = "report_pet"
    ) {
        // Pantalla para reportar una mascota
        composable("report_pet") {
            ReportPetScreen(
                onReportSubmitted = { /* Acción que sucederá al reportar una mascota */ },
                repository = repository,
                onViewReports = { navController.navigate("pet_reports") }
            )
        }

        // Pantalla que muestra los reportes de mascotas
        composable("pet_reports") {
            PetReportsScreen(repository = repository) // No es necesario el navController aquí
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    val repository = PetReportRepository() // Asegúrate de que el repositorio esté correctamente inicializado
    MainScreen(repository = repository)
}
