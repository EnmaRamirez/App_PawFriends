// PetReport.kt
package com.enma.pawfriends.ReporteMascotas

// Llamado al reporte de mascotas
data class PetReport(
    val id: String, // UUID o timestamp para identificar el reporte
    val name: String,
    val type: String, // Perdida o Encontrada
    val description: String,
    val location: String,
    val date: String, // Fecha del reporte
    val userId: String // ID del usuario que reportó la mascota
)
