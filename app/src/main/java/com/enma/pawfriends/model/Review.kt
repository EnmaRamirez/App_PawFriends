package com.enma.pawfriends.model

data class Review(
    val reviewerId: String,
    val reviewedId: String,
    val rating: Int,
    val comment: String,
    val timestamp: Long
)
