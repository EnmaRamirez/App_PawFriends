package com.enma.pawfriends.ReporteMascotas

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun PetListScreen(viewModel: PetReportViewModel = viewModel()) {
    val petReports by viewModel.petReports.collectAsState(initial = emptyList())

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(petReports) { pet ->
            Card(modifier = Modifier.padding(8.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Nombre: ${pet.name}")
                    Text("Descripción: ${pet.description}")
                    Text("Fecha: ${pet.date}")
                    Text("Ubicación: ${pet.location}")
                }
            }
        }
    }
}