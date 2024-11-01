package com.enma.pawfriends.serviciosmascotas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ServiceItem(service: Service, onServiceRequested: (Service) -> Unit)
{
    var showDialog by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Tipo: ${service.type}")
            Text("Descripción: ${service.description}")
            Text("Precio: ${if (service.isFree) "Gratis" else "${service.price}"}")
            Text("Ofrecido por: ${service.offeredBy}")
            Button(onClick = {
                onServiceRequested(service)
                showDialog = true
            }) {
                Text("Solicitar Servicio")
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Solicitud de Servicio") },
            text = { Text("Se ha solicitado el servicio ${service.type}") },
            confirmButton = {
                Button(onClick = { showDialog = false }) {
                    Text("OK")
                }
            }
        )
    }
}