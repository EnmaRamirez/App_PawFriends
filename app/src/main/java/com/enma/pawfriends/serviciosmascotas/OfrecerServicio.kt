package com.enma.pawfriends.serviciosmascotas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import java.util.UUID

@Composable
fun OfferServiceScreen(
    onServiceAdded: () -> Unit,
    navController: () -> Unit
) {
    var description by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var isFree by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Describe el servicio")
        Spacer(modifier = Modifier.height(8.dp))
        BasicTextField(
            value = description,
            onValueChange = { description = it },
            modifier = Modifier.fillMaxWidth(),
            decorationBox = { innerTextField ->
                if (description.isEmpty()) Text("Descripción")
                innerTextField()
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        BasicTextField(
            value = type,
            onValueChange = { type = it },
            modifier = Modifier.fillMaxWidth(),
            decorationBox = { innerTextField ->
                if (type.isEmpty()) Text("Tipo de Servicio (Paseo, Aseo, etc.)")
                innerTextField()
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = isFree,
                onCheckedChange = { isFree = it }
            )
            Text("¿Servicio Gratuito?")
        }
        if (!isFree) {
            BasicTextField(
                value = price,
                onValueChange = { price = it },
                modifier = Modifier.fillMaxWidth(),
                decorationBox = { innerTextField ->
                    if (price.isEmpty()) Text("Precio")
                    innerTextField()
                }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            scope.launch {
                val service = Service(
                    id = UUID.randomUUID().toString(),
                    description = description,
                    type = type,
                    price = if (isFree) null else price.toDoubleOrNull(),
                    isFree = isFree,
                    ownerId = "" // Aquí podrías agregar el nombre del usuario actual
                )
                ServiceRepository.addService(service)
                onServiceAdded()
            }
        }) {
            Text("Publicar Servicio")
        }
    }
}