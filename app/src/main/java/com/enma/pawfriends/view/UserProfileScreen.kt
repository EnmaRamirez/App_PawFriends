package com.enma.pawfriends.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.enma.pawfriends.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

@Composable
fun UserProfileScreen(navController: NavController) {
    val firestore = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()
    val userId = auth.currentUser?.uid ?: return
    val user = remember { mutableStateOf<User?>(null) }
    val errorMessage = remember { mutableStateOf<String?>(null) }

    // Lógica para obtener los datos del usuario de Firestore
    LaunchedEffect(userId) {
        try {
            val document = firestore.collection("Users").document(userId).get().await()
            user.value = document.toObject(User::class.java)
        } catch (e: Exception) {
            errorMessage.value = "Error al cargar los datos del usuario."
        }
    }

    // Composición para mostrar el perfil del usuario o mensaje de error
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Perfil de Usuario",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        user.value?.let {
            // Mostramos la información del usuario
            Text(text = "Nombre: ${it.name}", fontSize = 20.sp, modifier = Modifier.padding(bottom = 8.dp))
            Text(text = "Correo: ${it.email}", fontSize = 18.sp, modifier = Modifier.padding(bottom = 8.dp))
            Text(text = "Puntos: ${it.points}", fontSize = 18.sp, modifier = Modifier.padding(bottom = 8.dp))
            if (it.recognitions.isNotEmpty()) {
                Text(
                    text = "Reconocimientos: ${it.recognitions.joinToString(", ")}",
                    fontSize = 18.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
        }

        // Mostramos el mensaje de error si hubo un problema
        errorMessage.value?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}
