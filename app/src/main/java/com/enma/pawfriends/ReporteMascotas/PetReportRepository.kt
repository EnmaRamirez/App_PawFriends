package com.enma.pawfriends.ReporteMascotas

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class PetReportRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    companion object {
        private const val TAG = "PetReportRepository"
    }

    // Función para obtener los reportes de mascotas
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
                    userId = document.getString("userId") ?: ""
                )
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error al obtener los reportes de mascotas: ${e.localizedMessage}", e)
            emptyList() // Retorna una lista vacía en caso de error
        }
    }

    // Función para reportar una mascota
    suspend fun reportPet(petReport: PetReport) {
        try {
            val userId = auth.currentUser?.uid
            if (userId == null) {
                Log.e(TAG, "Usuario no autenticado. No se puede reportar la mascota.")
                return
            }

            val reportWithUserId = petReport.copy(userId = userId)
            firestore.collection("pet_reports").document(reportWithUserId.id).set(reportWithUserId).await()
            Log.d(TAG, "Reporte de mascota creado exitosamente con ID: ${reportWithUserId.id}")
        } catch (e: Exception) {
            Log.e(TAG, "Error al reportar la mascota: ${e.localizedMessage}", e)
        }
    }
}
