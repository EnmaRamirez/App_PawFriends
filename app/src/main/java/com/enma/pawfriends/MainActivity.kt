package com.enma.pawfriends

import android.os.Bundle
import android.util.Log
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
import com.enma.pawfriends.repuesto.viewmodel.LoginViewModel
import com.enma.pawfriends.repuesto.viewmodel.NotesViewModel
import com.enma.pawfriends.services.FirestoreService
import com.enma.pawfriends.ui.theme.PawFriendsTheme
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging

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
                    NavManager(loginViewModel = loginViewModel, notesViewModel = notesViewModel)
                }
            }
        }
    }
}

@Composable
fun Elementos(navController: NavController) {
    val mContext = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .clip(shape = MaterialTheme.shapes.small)
                .background(MaterialTheme.colorScheme.secondary)
                .border(2.dp, MaterialTheme.colorScheme.primary)
        ) {
            Image(
                painter = painterResource(id = R.drawable.logitopaw),
                contentDescription = "Paw Friends",
                modifier = Modifier.padding(20.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Paw Friends",
            color = MaterialTheme.colorScheme.primaryContainer,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "Bienvenido, Usuario",
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.bodySmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            OutlinedButton(
                onClick = {
                    // Acción para "Tu mascota"
                    navController.navigate("pet_profile")
                }
            ) {
                Text("Tu mascota")
            }
            Spacer(modifier = Modifier.width(10.dp))
            OutlinedButton(
                onClick = {
                    navController.navigate("login")
                }
            ) {
                Text("Inicia sesión")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ElementosPreview() {
    PawFriendsTheme {
        // Necesitas proporcionar un NavController para probar
        // Aquí se puede usar un navController simulado
        // Elementos(navController = rememberNavController())
    }
}