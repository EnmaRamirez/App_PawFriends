package com.enma.pawfriends.services

import com.enma.pawfriends.model.Pet
import com.google.firebase.firestore.FirebaseFirestore


class FirestoreService {
    private val db = FirebaseFirestore.getInstance()

    fun savePet(pet: Pet, onSuccess: () -> Unit, onFailure: (Exception) -> Unit){
        db.collection("Register_pets").add(pet)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener{e -> onFailure(e)}
    }
}
