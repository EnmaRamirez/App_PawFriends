package com.enma.pawfriends.view.reviews

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.enma.pawfriends.model.Review
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewScreen(
    navController: NavController,
    onReviewSubmit: (Review) -> Unit
) {
    var rating by remember { mutableStateOf(0) }
    var comment by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Calificación", fontSize = 24.sp, modifier = Modifier.padding(bottom = 16.dp))

        // Selector de estrellas
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            for (i in 1..5) {
                IconButton(onClick = { rating = i }) {
                    Icon(
                        imageVector = if (i <= rating) Icons.Filled.Star else Icons.Outlined.Star,
                        contentDescription = "Rating $i"
                    )
                }
            }
        }

        // Campo de texto para el comentario
        OutlinedTextField(
            value = comment,
            onValueChange = { comment = it },
            label = { Text("Escribe tu reseña") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        )

        // Botón para enviar la reseña
        Button(
            onClick = {
                if (rating > 0) {
                    val review = Review(
                        reviewerId = "user1", // Placeholder, reemplazar con el ID actual
                        reviewedId = "user2", // Placeholder, reemplazar con el ID actual
                        rating = rating,
                        comment = comment,
                        timestamp = System.currentTimeMillis()
                    )
                    onReviewSubmit(review)
                }
            }
        ) {
            Text("Enviar Reseña")
        }
    }
}
