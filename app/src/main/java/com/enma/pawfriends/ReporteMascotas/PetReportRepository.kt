package com.enma.pawfriends.ReporteMascotas

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class PetReportRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    // Función para obtener los reportes de mascotas
    suspend fun getPetReports(): List<PetReport> {
        return try {
            val snapshot = firestore.collection("pet_reports").get().await()
            snapshot.documents.map { document ->
                PetReport(
                    id = document.id,
                    name = document.getString("name") ?: "",
                    type = document.getString("type") ?: "",
                    description = document.getString("description") ?: "",
                    location = document.getString("location") ?: "",
                    date = document.getString("date") ?: "",
                    ownerId = document.getString("ownerId") ?: ""
                )
            }
        } catch (e: Exception) {
            Log.e("PetReportRepository", "Error al obtener los reportes de mascotas", e)
            emptyList()
        }
    }

    // Función para reportar una mascota
    suspend fun reportPet(petReport: PetReport) {
        try {
            val email = auth.currentUser?.email ?: return
            val reportWithEmail = petReport.copy(ownerId = email)

            firestore.runTransaction { transaction ->
                val reportRef = firestore.collection("pet_reports").document(reportWithEmail.id)
                transaction.set(reportRef, reportWithEmail)

                // Incrementar puntos al usuario que realizó el reporte
                updatePointsForUserInTransaction(transaction, email, 10) // Incrementar 10 puntos por reportar una mascota
            }.await()

            Log.d("PetReportRepository", "Reporte de mascota guardado con éxito y puntos asignados")
        } catch (e: Exception) {
            Log.e("PetReportRepository", "Error al reportar la mascota", e)
        }
    }

    // Función para incrementar puntos de usuario dentro de una transacción
    private fun updatePointsForUserInTransaction(transaction: com.google.firebase.firestore.Transaction, email: String, points: Int) {
        val userRef = firestore.collection("Users").document(email)
        val snapshot = transaction.get(userRef)
        val currentPoints = snapshot.getLong("points") ?: 0
        transaction.update(userRef, "points", currentPoints + points)
    }

    // Función para incrementar puntos de usuario fuera de transacción (puede ser utilizada en otras funciones)
    suspend fun updatePointsForUser(email: String, points: Int) {
        try {
            val userRef = firestore.collection("Users").document(email)
            firestore.runTransaction { transaction ->
                val snapshot = transaction.get(userRef)
                val currentPoints = snapshot.getLong("points") ?: 0
                transaction.update(userRef, "points", currentPoints + points)
            }.await()
        } catch (e: Exception) {
            Log.e("PetReportRepository", "Error al actualizar los puntos del usuario", e)
        }
    }
}
