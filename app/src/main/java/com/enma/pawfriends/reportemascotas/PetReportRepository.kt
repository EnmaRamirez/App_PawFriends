package com.enma.pawfriends.reportemascotas

import PetReport
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class PetReportRepository {

    private val firestore = FirebaseFirestore.getInstance()

    suspend fun getPetReports(): List<PetReport> {
        return try {
            val snapshot = firestore.collection("pet_reports").get().await()
            val usersMap = getUsersMap() // Obtener el mapa de usuarios

            snapshot.documents.map { document ->
                val email = document.getString("userEmail") ?: ""
                PetReport(
                    id = document.getString("id") ?: "",
                    name = document.getString("name") ?: "",
                    type = document.getString("type") ?: "",
                    description = document.getString("description") ?: "",
                    location = document.getString("location") ?: "",
                    date = document.getString("date") ?: "",
                    userName = usersMap[email] ?: "Desconocido" // Obtener el nombre del usuario
                )
            }
        } catch (e: Exception) {
            e.printStackTrace() // Manejar errores
            emptyList() // Retorna una lista vacía en caso de error
        }
    }

    private suspend fun getUsersMap(): Map<String, String> {
        val usersMap = mutableMapOf<String, String>()
        val snapshot = firestore.collection("Users").get().await()
        for (document in snapshot.documents) {
            val email = document.getString("email") ?: ""
            val name = document.getString("name") ?: "" // Suponiendo que hay un campo "name" en Users
            usersMap[email] = name
        }
        return usersMap
    }

    suspend fun reportPet(petReport: PetReport) {
        try {
            firestore.collection("pet_reports").document(petReport.id).set(petReport).await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
