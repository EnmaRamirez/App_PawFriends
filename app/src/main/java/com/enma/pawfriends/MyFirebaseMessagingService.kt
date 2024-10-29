package com.enma.pawfriends

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        // Verificar si el mensaje contiene datos personalizados
        if (remoteMessage.data.isNotEmpty()) {
            val data = remoteMessage.data
            val title = data["title"] ?: "Notificación de Mascota"
            val message = data["body"] ?: "Una actualización sobre una mascota reportada."

            // Crear una notificación personalizada usando los datos
            sendNotification(title, message)
        } else {
            // Manejar notificación estándar (si no hay datos personalizados)
            val title = remoteMessage.notification?.title ?: "Nueva Notificación"
            val message = remoteMessage.notification?.body ?: ""

            // Crear una notificación
            sendNotification(title, message)
        }
    }

    private fun sendNotification(title: String, message: String) {
        val channelId = "notification_channel"
        val notificationId = 1001

        // Crear un Intent para abrir la actividad cuando el usuario haga clic en la notificación
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info) // Icono de la notificación actualizado para evitar errores
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        // Crear el canal de notificación (necesario para Android 8.0+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Notificaciones App",
                NotificationManager.IMPORTANCE_HIGH
            )
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(channel)
        }

        // Mostrar la notificación
        with(NotificationManagerCompat.from(this)) {
            if (ActivityCompat.checkSelfPermission(
                    this@MyFirebaseMessagingService,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // Solicitar permisos si no están concedidos
                // ActivityCompat#requestPermissions debe ser llamado desde una actividad, no desde un servicio
                return
            }
            notify(notificationId, notificationBuilder.build())
        }
    }
}