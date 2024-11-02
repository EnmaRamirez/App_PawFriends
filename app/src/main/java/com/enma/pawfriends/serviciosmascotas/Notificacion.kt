package com.enma.pawfriends.serviciosmascotas

fun notifyServiceAccepted(service: Any, requestingUser: String) {
    println("El usuario $requestingUser ha aceptado tu servicio de ${service.type}.")
}