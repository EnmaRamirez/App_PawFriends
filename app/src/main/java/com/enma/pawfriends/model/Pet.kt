package com.enma.pawfriends.model
//Registro de mascotas

data class Pet (
    val name: String = "",
    val breed: String = "",
    val age: Int = 0,
    val health: String = "",
    val carnet: Int = 0,
    val ownerId: String = ""
)

data class PetAd(
    val petId: String,
    val ownerId: String,
    val description: String,
    val medicalHistory: String,
    val photos: List<String> = listOf()
)
data class AdoptionRequest(
    val requestId: String,      // ID único de la solicitud
    val petId: String,          // ID de la mascota en adopción
    val requesterId: String,    // ID del usuario que realiza la solicitud de adopción
    val status: String = "pending", // Estado de la solicitud (ej. "pending", "accepted", "rejected")
    val message: String? = null  // Mensaje adicional opcional
)