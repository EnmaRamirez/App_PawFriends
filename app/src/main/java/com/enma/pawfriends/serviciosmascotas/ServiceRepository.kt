package com.enma.pawfriends.serviciosmascotas

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

object ServiceRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val servicesCollection = firestore.collection("services")

    suspend fun addService(service: Service) {
        try {
            servicesCollection.document(service.id).set(service).await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun getServices(): List<Service> {
        return try {
            val snapshot = servicesCollection.get().await()
            snapshot.toObjects(Service::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}