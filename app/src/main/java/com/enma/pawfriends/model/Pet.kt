package com.enma.pawfriends.model

data class Pet(
    val name: String = "",
    val breed: String = "",
    val age: Int = 0,
    val health: String = "",
    val carnet: Int = 0,
    val ownerId: String = "",
    val imageUrl: String = "",  // URL de la imagen subida
    val videoUrl: String = ""   // URL del video (opcional)
)
