package com.enma.pawfriends.reportemascotas

data class PetReport(
    val id: String,
    val name: String,
    val type: String,
    val description: String,
    val location: String,
    val date: String,
    val imageUrl: String? = null // Nueva propiedad para la URL de la imagen
)
