package com.enma.pawfriends

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ReviewActivity : ComponentActivity() {
    private val db = Firebase.firestore // Instancia de Firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ReviewAndRatingScreen(db, onBackClicked = { finish() }) // Pasa la instancia de Firestore y la acción de regresar
        }
    }
}

@Composable
fun ReviewAndRatingScreen(db: FirebaseFirestore, onBackClicked: () -> Unit) {
    var rating by remember { mutableStateOf(0) }
    var reviewText by remember { mutableStateOf(TextFieldValue("")) }
    var appreciationText by remember { mutableStateOf(TextFieldValue("")) }
    var reviewSubmitted by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!reviewSubmitted) {
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
            BasicTextField(
                value = reviewText,
                onValueChange = { reviewText = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(Color.LightGray),
                decorationBox = { innerTextField ->
                    Box(modifier = Modifier.padding(8.dp)) {
                        if (reviewText.text.isEmpty()) {
                            Text("Escribe una reseña detallada sobre tu experiencia...")
                        }
                        innerTextField()
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo de texto para los agradecimientos
            BasicTextField(
                value = appreciationText,
                onValueChange = { appreciationText = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(Color.LightGray),
                decorationBox = { innerTextField ->
                    Box(modifier = Modifier.padding(8.dp)) {
                        if (appreciationText.text.isEmpty()) {
                            Text("Deja un agradecimiento por la adopción, cuidado temporal u otros servicios...")
                        }
                        innerTextField()
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botón para enviar la reseña
            Button(
                onClick = {
                    // Aquí guardamos la reseña en Firestore
                    val review = hashMapOf(
                        "rating" to rating,
                        "reviewText" to reviewText.text,
                        "appreciationText" to appreciationText.text
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
                }
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
            Text(text = "Reseña: ${reviewText.text}", fontSize = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))
            if (appreciationText.text.isNotEmpty()) {
                Text(text = "Agradecimiento: ${appreciationText.text}", fontSize = 18.sp)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para regresar a la pantalla principal
        Button(onClick = { onBackClicked() }) {
            Text("Regresar a la pantalla principal")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReviewAndRatingScreenPreview() {
    ReviewAndRatingScreen(Firebase.firestore, onBackClicked = {}) // Proporciona una instancia de Firestore para la vista previa
}
