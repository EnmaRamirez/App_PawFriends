package com.enma.pawfriends.utils

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
import com.enma.pawfriends.viewmodel.MessagingViewModel
import com.google.firebase.messaging.RemoteMessage.Notification

@Composable
fun NotificationUtils(navController: NavController, viewModel: MessagingViewModel = viewModel()) {
    val messages by viewModel.messages.collectAsState()
    val newMessage = remember { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(messages) { message ->
                Text(text = message, style = MaterialTheme.typography.bodyMedium)
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
                    viewModel.sendMessage(newMessage.value)
                    newMessage.value = ""
                }
            }, modifier = Modifier.padding(start = 8.dp)) {
                Text("Enviar")
            }
            }
    }
}