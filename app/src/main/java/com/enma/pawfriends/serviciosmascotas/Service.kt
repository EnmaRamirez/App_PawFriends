package com.enma.pawfriends.serviciosmascotas

data class Service(
    val id: String = "",
    val description: String = "",
    val type: String = "",
    val price: Double? = null,
    val isFree: Boolean = false,
    val offeredBy: String = ""
)