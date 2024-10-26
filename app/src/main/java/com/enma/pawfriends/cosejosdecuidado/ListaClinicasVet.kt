package com.enma.pawfriends.cosejosdecuidado

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class ClinicaVeterinaria(
    val nombre: String,
    val direccion: String,
    val telefono: String
)

@Composable
fun ListaClinicasVeterinarias() {
    val clinicas = listOf(
        ClinicaVeterinaria("Clínica PetSave", "Calle 3, Jalapa", "3344-2215"),
        ClinicaVeterinaria("Clínica CleanPaw", "Calle 4, Jalapa", "1122-3344"),
        ClinicaVeterinaria("Clínica PawFriends", "Calle Principal los perros, Jalapa", "5566-7788"),
        ClinicaVeterinaria("Safe Pets Clinic", "Calle 2-4, Mataquescuintla", "1122-3344"),

        )

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Clínicas Veterinarias Cercanas",
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn {
            items(clinicas) { clinica ->
                Text(text = clinica.nombre, fontSize = 18.sp, modifier = Modifier.padding(8.dp))
                Text(text = "Dirección: ${clinica.direccion}", modifier = Modifier.padding(8.dp))
                Text(text = "Teléfono: ${clinica.telefono}", modifier = Modifier.padding(8.dp))
            }
        }
    }
}