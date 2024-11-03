package com.enma.pawfriends.services

import com.enma.pawfriends.model.Pet
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreService {
    private val db = FirebaseFirestore.getInstance()

    fun savePet(pet: Pet, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        // Genera un ID único para el documento
        val documentId = "${pet.name}_${pet.ownerId}" // Ajusta si es necesario

        db.collection("Register_pets").document(documentId)
            .set(pet)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e ->
                // Manejo detallado de errores
                onFailure("Error al registrar la mascota: ${e.message}")
            }
    }
}
