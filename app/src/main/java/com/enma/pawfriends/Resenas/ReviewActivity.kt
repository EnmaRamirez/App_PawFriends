package com.enma.pawfriends

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun ReviewAndRatingScreen(db: FirebaseFirestore) {
    var rating by rememberSaveable { mutableStateOf(0) }
    var reviewText by rememberSaveable { mutableStateOf("") }
    var appreciationText by rememberSaveable { mutableStateOf("") }
    var reviewSubmitted by rememberSaveable { mutableStateOf(false) }
    val navController = rememberNavController()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!reviewSubmitted) {
            Spacer(modifier = Modifier.height(16.dp))
            Text("Califica tu experiencia", fontSize = 20.sp)
            Spacer(modifier = Modifier.height(16.dp))

            // Estrellas de calificación
            Row(
                modifier = Modifier.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                (1..5).forEach { star ->
                    IconButton(
                        onClick = { rating = star }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = "Estrella $star",
                            tint = if (star <= rating) Color.Yellow else Color.Gray,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Campo de texto para la reseña
            TextField(
                value = reviewText,
                onValueChange = { reviewText = it },
                label = { Text("Escribe una reseña detallada sobre tu experiencia con el usuario...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(Color.LightGray)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo de texto para los agradecimientos
            TextField(
                value = appreciationText,
                onValueChange = { appreciationText = it },
                label = { Text("Deja un agradecimiento por la adopción, cuidado temporal u otros servicios...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(Color.LightGray)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botón para enviar la reseña
            Button(
                onClick = {
                    // Aquí guardamos la reseña en Firestore
                    val review = hashMapOf(
                        "rating" to rating,
                        "reviewText" to reviewText,
                        "appreciationText" to appreciationText
                    )

                    db.collection("reseñas") // Nombre de la colección en Firestore
                        .add(review)
                        .addOnSuccessListener {
                            reviewSubmitted = true // Cambia el estado para indicar que la reseña fue enviada
                        }
                        .addOnFailureListener { e ->
                            // Maneja cualquier error que ocurra al guardar la reseña
                            println("Error al agregar reseña: $e")
                        }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Enviar reseña")
            }
        } else {
            // Mostrar reseña y agradecimiento
            Text("Gracias por tu reseña!", fontSize = 20.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Row {
                (1..5).forEach { star ->
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = "Estrella $star",
                        tint = if (star <= rating) Color.Yellow else Color.Gray,
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Reseña: $reviewText", fontSize = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))
            if (appreciationText.isNotEmpty()) {
                Text(text = "Agradecimiento: $appreciationText", fontSize = 18.sp)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

    }
}