package com.enma.pawfriends.mensages.chat.ui1

data class Message(
    val userEmail: String = "", // Propiedad del remitente
    val content: String = "",   // Contenido del mensaje
    val timestamp: Long = System.currentTimeMillis() // Marca de tiempo para ordenar los mensajes
)
