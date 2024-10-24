package com.enma.pawfriends.reportemascotas

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class PetReportRepository {

    private val firestore = FirebaseFirestore.getInstance()

    suspend fun getPetReports(): List<PetReport> {
        return try {
            val snapshot = firestore.collection("pet_reports").get().await()
            snapshot.documents.map { document ->
                PetReport(
                    id = document.getString("id") ?: "",
                    name = document.getString("name") ?: "",
                    type = document.getString("type") ?: "",
                    description = document.getString("description") ?: "",
                    location = document.getString("location") ?: "",
                    date = document.getString("date") ?: "",
                )
            }
        } catch (e: Exception) {
            e.printStackTrace() // Manejar errores
            emptyList() // Retorna una lista vacía en caso de error
        }
    }

    suspend fun reportPet(petReport: PetReport) {
        try {
            firestore.collection("pet_reports").document(petReport.id).set(petReport).await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
