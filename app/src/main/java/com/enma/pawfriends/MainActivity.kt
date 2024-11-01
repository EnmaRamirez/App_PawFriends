package com.enma.pawfriends

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.enma.pawfriends.navigation.NavManager
import com.enma.pawfriends.services.FirestoreService
import com.enma.pawfriends.ui.theme.PawFriendsTheme
import com.enma.pawfriends.viewmodel.LoginViewModel
import com.enma.pawfriends.viewmodel.NotesViewModel
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import android.util.Log

class MainActivity : ComponentActivity() {
    private val firestoreService = FirestoreService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        enableEdgeToEdge()

        // Obtener token FCM
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val token = task.result
                Log.d("FCM Token", "Token: $token")
                // Puedes guardar el token en tu backend o Firestore
            } else {
                Log.e("FCM Token", "Error al obtener el token", task.exception)
            }
        }

        val loginViewModel: LoginViewModel by viewModels()
        val notesViewModel: NotesViewModel by viewModels()

        setContent {
            PawFriendsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavManager(loginViewModel, notesViewModel)
                }
            }
        }
    }
}
