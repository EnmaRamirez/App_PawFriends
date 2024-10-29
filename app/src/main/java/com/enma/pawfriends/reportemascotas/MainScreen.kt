import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.enma.pawfriends.reportemascotas.PetReportRepository


@Composable
fun MainScreen(navController: NavHostController, repository: PetReportRepository) {
    // Definimos el NavHost para la navegación
    NavHost(
        navController = navController,
        startDestination = "report_pet" // Pantalla inicial
    ) {
        // Composable para la pantalla de reportar mascotas
        composable("report_pet") {
            ReportPetScreen(
                onReportSubmitted = { /* Acción al reportar */ },
                onViewReports = { navController.navigate("pet_reports") },
                repository = repository
            )
        }
        // Composable para la pantalla que muestra los reportes de mascotas
        composable("pet_reports") {
            PetReportsScreen(repository = repository)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    val repository = PetReportRepository()
    val navController = rememberNavController() // Necesario para la preview
    MainScreen(navController = navController, repository = repository)
}
