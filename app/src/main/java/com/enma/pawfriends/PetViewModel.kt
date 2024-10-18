package com.enma.pawfriends

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class PetViewModel : ViewModel() {
    private val storage: FirebaseStorage = FirebaseStorage.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun reportPet(name: String, description: String, location: String, date: String, imageUri: Uri?) {
        val pet = hashMapOf(
            "name" to name,
            "description" to description,
            "location" to location,
            "date" to date
        )

        if (imageUri != null) {
            val ref = storage.reference.child("pets/${UUID.randomUUID()}")
            ref.putFile(imageUri).addOnSuccessListener {
                ref.downloadUrl.addOnSuccessListener { uri ->
                    pet["imageUrl"] = uri.toString()
                    savePetToFirestore(pet)
                }
            }
        } else {
            savePetToFirestore(pet)
        }
    }

    private fun savePetToFirestore(pet: Map<String, Any>) {
        firestore.collection("pets")
            .add(pet)
            .addOnSuccessListener {
                // Acción en caso de éxito
            }
            .addOnFailureListener {
                // Acción en caso de error
            }
    }
}
