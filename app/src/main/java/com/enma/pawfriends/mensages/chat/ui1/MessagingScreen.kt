package com.enma.pawfriends.chat.ui1

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.enma.pawfriends.chat.viewmodel.MessagingViewModel
import com.enma.pawfriends.viewmodel.LoginViewModel

@Composable
fun MessagingScreen(navController: NavController, messagingViewModel: MessagingViewModel = viewModel(), loginViewModel: LoginViewModel = viewModel()) {
    val messages by messagingViewModel.messages.collectAsState()
    val newMessage = remember { mutableStateOf("") }
    val userEmail = loginViewModel.getCurrentUserEmail() ?: "Usuario Anónimo" // Obtener correo electrónico

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(messages) { message ->
                // Aquí se muestra el correo electrónico junto con el contenido del mensaje
                Text(text = "${message.userEmail}: ${message.content}", style = MaterialTheme.typography.bodyMedium)
            }
        }
        Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
            TextField(
                value = newMessage.value,
                onValueChange = { newMessage.value = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Escribe un mensaje...") }
            )
            Button(onClick = {
                if (newMessage.value.isNotBlank()) {
                    messagingViewModel.sendMessage(newMessage.value, userEmail) // Envía el contenido del mensaje y el correo electrónico
                    newMessage.value = ""
                }
            }, modifier = Modifier.padding(start = 8.dp)) {
                Text("Enviar")
            }
        }
    }
}
