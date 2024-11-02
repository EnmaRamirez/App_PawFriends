package com.enma.pawfriends.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
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
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

@Composable
fun LeaderboardScreen(navController: NavController) {
    val firestore = FirebaseFirestore.getInstance()
    val users = remember { mutableStateOf<List<User>>(emptyList()) }

    LaunchedEffect(Unit) {
        try {
            val snapshot = firestore.collection("Users")
                .orderBy("points", com.google.firebase.firestore.Query.Direction.DESCENDING)
                .get().await()
            users.value = snapshot.documents.mapNotNull { it.toObject(User::class.java) }
        } catch (e: Exception) {
            e.printStackTrace() // Puedes manejar el error aquí si lo deseas
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Clasificación de Usuarios",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            items(users.value) { user ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    Text(text = "Nombre: ${user.name}", fontSize = 20.sp)
                    Text(text = "Puntos: ${user.points}", fontSize = 18.sp)
                    if (user.recognitions.isNotEmpty()) {
                        Text(
                            text = "Reconocimientos: ${user.recognitions.joinToString(", ")}",
                            fontSize = 18.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.navigate("elementos") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Volver al Menú Principal")
        }
    }
}
