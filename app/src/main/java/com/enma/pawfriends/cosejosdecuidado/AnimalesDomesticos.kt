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
fun AnimalesDomesticos(navController: NavController) {
    // Lista de mascotas, incluyendo la imagen, el texto del AlertDialog y el enlace al video
    val mascotas = listOf(
        Triple("Perros", R.drawable.perro, "https://youtu.be/TbgB5zH8_3E?si=yjghY6a_JmBiKkl2"),
        Triple("Gatos", R.drawable.gato, "https://youtu.be/gXRCjKd4jIs?si=s63m_T2eLodfl8sG"),
        Triple("Aves", R.drawable.ave, "https://youtu.be/CFK4inlsfO4?si=RlMDf-pr6nAeeqQN"),
        Triple("Roedores", R.drawable.roedor, "https://youtu.be/aoPggCxDsxY?si=Q_-bCEi57NesV_85")
    )

    // Textos específicos para cada animal
    val recomendacionesTextos = mapOf(
        "Perros" to "Cuidar a un perro implica compromiso y atención. En general, es importante procurar una alimentación saludable...",
        "Gatos" to "Los gatos son animales independientes, pero requieren de cuidados como buena alimentación, higiene y visitas regulares al veterinario...",
        "Aves" to "Las aves necesitan un ambiente seguro y saludable. Es importante mantener sus jaulas limpias y darles una dieta equilibrada...",
        "Roedores" to "Los roedores requieren jaulas limpias, una dieta equilibrada y un entorno seguro. Es importante proporcionarles juguetes para roer..."
    )

    val context = LocalContext.current

    // Estado para controlar cuál cuadro de diálogo mostrar
    var selectedAnimal by remember { mutableStateOf<String?>(null) }

    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        items(mascotas) { (mascota, imageResId, videoUrl) ->
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