package com.enma.pawfriends.view.reviews

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.enma.pawfriends.model.Review

@Composable
fun ReviewList(reviews: List<Review>) {
    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        items(reviews) { review ->
            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                Text(text = "Calificación: ${review.rating} estrellas")
                Text(text = "Comentario: ${review.comment}")
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}
