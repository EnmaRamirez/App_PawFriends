package com.enma.pawfriends.mensages.chat.ui1
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.enma.pawfriends.mensages.chat.utils.createNotificationChannel

@Composable
fun MainScreen() {
    val context = LocalContext.current


    LaunchedEffect(key1 = context) {
        createNotificationChannel(context)
    }

    val navController = rememberNavController()

    // Llamada al composable App pasándole el navController
    App(navController = navController)
}