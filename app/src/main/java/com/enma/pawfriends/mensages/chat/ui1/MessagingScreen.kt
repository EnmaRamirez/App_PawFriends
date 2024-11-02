package com.enma.pawfriends.chat.ui1

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.enma.pawfriends.mensages.chat.ui1.ChatNavigationBar
import com.enma.pawfriends.mensages.chat.ui1.Mensaje
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.Timestamp
import java.util.*

@Composable
fun MessagingScreen(uid: String, navController: NavHostController) {
    val db = FirebaseFirestore.getInstance()  // Conexión a Firestore
    val mensajes = remember { mutableStateListOf<Mensaje>() }
    var mensajeTexto by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(true) }

    // Cargar mensajes desde Firestore
    LaunchedEffect(Unit) {
        db.collection("chat")
            .orderBy("timestamp")
            .addSnapshotListener { snapshot, e ->
                if (e != null) return@addSnapshotListener
                mensajes.clear()
                snapshot?.documents?.forEach { document ->
                    val mensaje = document.toObject(Mensaje::class.java)
                    if (mensaje != null) mensajes.add(mensaje)
                }
                loading = false
            }
    }

    Scaffold(
        bottomBar = { ChatNavigationBar(navController, uid) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF1F1F1))
        ) {
            Text(
                text = "Chat Global",
                fontSize = 24.sp,
                modifier = Modifier.padding(16.dp),
                color = Color(0xFF000080)
            )

            if (loading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                        .align(Alignment.CenterHorizontally)
                )
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(mensajes) { mensaje ->
                        MessageCard(mensaje)
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = mensajeTexto,
                    onValueChange = { mensajeTexto = it },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Escribe un mensaje...") }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = {
                        if (mensajeTexto.isNotBlank()) {
                            val nuevoMensaje = Mensaje(
                                autorId = uid,
                                contenido = mensajeTexto,
                                timestamp = Timestamp(Date()).toDate().toString()
                            )
                            db.collection("chat").add(nuevoMensaje)
                            mensajeTexto = ""
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2))
                ) {
                    Text("Enviar")
                }
            }
        }
    }
}

@Composable
fun MessageCard(mensaje: Mensaje) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Autor: ${mensaje.autorId}",
                fontSize = 14.sp,
                color = Color.Gray
            )
            Text(text = mensaje.contenido, fontSize = 16.sp)
            Text(
                text = mensaje.timestamp,
                fontSize = 12.sp,
                color = Color.LightGray
            )
        }
    }
}