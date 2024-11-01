package com.enma.pawfriends.reportemascotas

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun MainScreen(navController: NavHostController = rememberNavController(), repository: PetReportRepository) {
    NavHost(
        navController = navController,
        startDestination = "report_pet"
    ) {
        composable("report_pet") {
            ReportPetScreen(
                onReportSubmitted = { /* Acción al reportar una mascota */ },
                repository = repository,
                onViewReports = { navController.navigate("pet_reports") }
            )
        }

        composable("pet_reports") {
            PetReportsScreen(repository = repository)
        }
    }
}

// Mock para el repositorio en la vista previa
class MockPetReportRepository : PetReportRepository() {
    // Implementa métodos simulados si es necesario
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    val repository = MockPetReportRepository() // Usar el mock aquí
    MainScreen(repository = repository)
}
