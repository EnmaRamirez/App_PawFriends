package com.enma.pawfriends.reportemascotas

import androidx.compose.foundation.layout.* // Import para la UI
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@Composable
fun PetReportsScreen(navController: NavController, repository: PetReportRepository) {
    val coroutineScope = rememberCoroutineScope()
    var petReports by remember { mutableStateOf<List<PetReport>>(emptyList()) } // Lista para almacenar los reportes

    // Lanzamos una corrutina para obtener los datos
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            petReports = repository.getPetReports() // Llamada al repositorio para obtener los datos
        }
    }

    // Mostrar los datos en una LazyColumn
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(petReports) { report ->
            PetReportItem(report) // Usamos un Composable para mostrar cada reporte
        }
    }
}

@Composable
fun PetReportItem(report: PetReport) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text("Nombre: ${report.name}")
        Text("Tipo: ${report.type}")
        Text("Descripción: ${report.description}")
        Text("Ubicación: ${report.location}")
        Text("Última fecha vista: ${report.date}")
        Spacer(modifier = Modifier.height(8.dp))
    }
}