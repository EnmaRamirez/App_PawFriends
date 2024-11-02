package com.enma.pawfriends.model

data class User(
    val email: String,
    val userId: String,
    val name: String,
    var points: Int = 0,
    var recognitions: List<Any> = mutableListOf() // Almacena reconocimientos como "Rescatador del Mes"
) {
    fun toMap(): MutableMap<String, Any> {
        return mutableMapOf(
            "email" to email,
            "userId" to userId,
            "name" to name,
            "points" to points,
            "recognitions" to recognitions
        )
    }
}
