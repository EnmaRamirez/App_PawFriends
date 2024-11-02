package com.enma.pawfriends.mensages.chat.ui1

import com.google.firebase.firestore.PropertyName

data class Mensaje(
    @PropertyName("autorId") val autorId: String = "",
    @PropertyName("contenido") val contenido: String = "",
    @PropertyName("timestamp") val timestamp: String = "",


)
