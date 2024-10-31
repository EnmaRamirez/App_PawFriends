// FirestoreService.kt
package com.enma.pawfriends.services

import android.net.Uri
import com.enma.pawfriends.model.Pet
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class FirestoreService {

    private val firestore = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance().reference

    fun uploadMediaFile(uri: Uri, onSuccess: (String) -> Unit, onError: (Exception) -> Unit) {
        val filePath = "pets/${UUID.randomUUID()}"
        val fileRef = storage.child(filePath)

        fileRef.putFile(uri)
            .addOnSuccessListener {
                fileRef.downloadUrl.addOnSuccessListener { downloadUri -> onSuccess(downloadUri.toString()) }
            }
            .addOnFailureListener { e -> onError(e) }
    }

    fun savePet(pet: Pet, onSuccess: () -> Unit, onError: (Exception) -> Unit) {
        firestore.collection("pets")
            .add(pet)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onError(e) }
    }

    fun getPets(onSuccess: (List<Pet>) -> Unit, onError: (Exception) -> Unit) {
        firestore.collection("pets")
            .get()
            .addOnSuccessListener { querySnapshot ->
                val pets = querySnapshot.documents.mapNotNull { it.toObject(Pet::class.java) }
                onSuccess(pets)
            }
            .addOnFailureListener { e -> onError(e) }
    }
}
