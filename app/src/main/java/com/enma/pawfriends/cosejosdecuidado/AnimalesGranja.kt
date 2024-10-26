package com.enma.pawfriends.cosejosdecuidado

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.enma.pawfriends.R

@Composable
fun AnimalesGranja(navController: NavController) {
    // Lista de mascotas, incluyendo la imagen, el texto del AlertDialog y el enlace al video
    val granja = listOf(
        Triple("Gallinas", R.drawable.gallina, "https://youtu.be/xyybHnk5UT8?si=xCZcFOwtW3t60gZn"),
        Triple("Vacas", R.drawable.vaca, "https://youtu.be/Wfz23z0Wyq4?si=3BUJx3_WDy9lAPRb"),
        Triple("Cabras", R.drawable.cabra, "https://youtu.be/qWThipHOjyk?si=d397kpbs8ry3iiyr"),
    )

    // Textos específicos para cada animal
    val recomendacionesTextos = mapOf(
        "Gallinas" to "Si no quieres que tus gallinas enfermen, es imprescindible desparasitarlas interna y externamente de forma periódica.Se recomienda desparasitar al menos dos veces al año, en primavera y otoño, como medida preventiva...",
        "Vacas" to "Los cuidados del ganado deben ser realizados por un profesional de la salud animal, un veterinario especializado en ganadería es la mejor alternativa. De igual manera la aplicación de medicamentos...",
        "Cabras" to "Cuando se manipule a las cabras en los espacios pecuarios es preciso ser sumamente gentil y apacible. Las cabras requieren de un recinto techado y no un simple cobertizo como refugio. También es positivo que sus recintos tengan...",
    )

    val context = LocalContext.current

    // Estado para controlar cuál cuadro de diálogo mostrar
    var selectedAnimal by remember { mutableStateOf<String?>(null) }

    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        items(granja) { (mascota, imageResId, videoUrl) ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                // Imagen representativa del animal
                Image(
                    painter = painterResource(id = imageResId),
                    contentDescription = "Imagen de $mascota",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(250.dp)
                        .padding(bottom = 8.dp)
                )

                // Nombre del animal
                Text(text = mascota, fontSize = 18.sp, modifier = Modifier.padding(8.dp))

                // Botón para mostrar las recomendaciones generales específicas por animal
                Button(
                    onClick = { selectedAnimal = mascota }, // Muestra el cuadro de diálogo de este animal
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                ) {
                    Text(text = "Recomendaciones Generales")
                }

                // Mostrar el AlertDialog específico según el animal seleccionado
                if (selectedAnimal == mascota) {
                    AlertDialog(
                        onDismissRequest = { selectedAnimal = null },
                        title = { Text(text = "Recomendaciones para $mascota") },
                        text = {
                            Text(text = recomendacionesTextos[mascota] ?: "No hay recomendaciones disponibles.")
                        },
                        confirmButton = {
                            Button(onClick = { selectedAnimal = null }) {
                                Text(text = "Cerrar")
                            }
                        }
                    )
                }

                // Botón para ver el video de cuidado específico para cada animal
                Button(
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl))
                        context.startActivity(intent)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Ver Video de Cuidado")
                }
            }
        }
    }
}