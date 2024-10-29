package com.enma.pawfriends

sealed class Destinos (
    val icon : Int,
    val title : String,
    val ruta : String
){
    object Pantalla1: Destinos(R.drawable.registrosmascotas, "Registro y gestión de mascotas", "Pantalla1")
    object Pantalla2: Destinos(R.drawable.reporte, "Reporte de mascotas perdidas y encontradas", "Pantalla2")
    object Pantalla3: Destinos(R.drawable.adopcion, "Adopciones de mascotas", "Pantalla3")
    object Pantalla4: Destinos(R.drawable.servicios, "Servicios de mascotas", "Pantalla4")
    object Pantalla5: Destinos(R.drawable.saludveterinario, "Consejos de cuidados y recursos veterinarios", "Pantalla5")
    object Pantalla6: Destinos(R.drawable.mensajeriad, "Mensajería directa", "Pantalla6")
    object Pantalla7: Destinos(R.drawable.notificaciones, "Notificaciones", "Pantalla7")
    object Pantalla8: Destinos(R.drawable.calificaciones, "Calificación y reseñas", "Pantalla8")
    object Pantalla9: Destinos(R.drawable.recompensas, "Recompensas y reconocimientos", "Pantalla9")

}

