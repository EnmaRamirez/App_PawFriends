package com.enma.pawfriends.reportemascotas

import android.net.Uri
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

class PetReportRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance().reference

    // Función para obtener los reportes de mascotas
    suspend fun getPetReports(): List<PetReport> {
        return try {
            val snapshot = firestore.collection("pet_reports").get().await() // Obtiene los documentos de Firestore
            snapshot.documents.map { document ->
                PetReport(
                    id = document.getString("id") ?: "",
                    name = document.getString("name") ?: "",
                    type = document.getString("type") ?: "",
                    description = document.getString("description") ?: "",
                    location = document.getString("location") ?: "",
                    date = document.getString("date") ?: "",
                    imageUrl = document.getString("imageUrl") // URL de la imagen
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    // Función para reportar una mascota
    suspend fun reportPet(petReport: PetReport) {
        firestore.collection("pet_reports").document(petReport.id).set(petReport).await()
    }

    // Función para subir la imagen a Firebase Storage
    suspend fun uploadPetImage(imageUri: Uri, petId: String): String? {
        return try {
            val imageRef = storage.child("pet_reports/$petId.jpg")
            imageRef.putFile(imageUri).await()
            imageRef.downloadUrl.await().toString() // Retorna la URL de descarga
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
