package com.enma.pawfriends.mensages


data class Message(
    val userId: String = "",
    val username: String = "",


    val message: String = "",


    val timestamp: Long = System.currentTimeMillis()
)