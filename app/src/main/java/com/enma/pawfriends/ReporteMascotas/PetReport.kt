package com.enma.pawfriends.ReporteMascotas

// Modelo de datos del reporte de mascotas
data class PetReport(
    val id: String = "", // Identificador único para el reporte (puede ser un UUID o timestamp)
    val name: String = "", // Nombre de la mascota
    val type: String = "", // Tipo de reporte: Perdida o Encontrada
    val description: String = "", // Descripción de la mascota
    val location: String = "", // Ubicación donde se vio por última vez
    val date: String = "", // Fecha del reporte
    val ownerId: String = "" // Correo electrónico del dueño
) {
    // Método para convertir el reporte en un mapa, para guardar en Firestore
    fun toMap(): Map<String, Any> {
        return mapOf(
            "id" to id,
            "name" to name,
            "type" to type,
            "description" to description,
            "location" to location,
            "date" to date,
            "ownerId" to ownerId
        )
    }
}
