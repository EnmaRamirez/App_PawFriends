package com.enma.pawfriends.cosejosdecuidado

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.enma.pawfriends.R

@Composable
fun CategoriasDeAnimales(navController: NavController,
                         onAnimalDomClick: () -> Unit,
                         onAnimalGranClick: () -> Unit) {
    val animalesDomesticos = listOf(
        "Animales Domésticos" to R.drawable.animales_domesticos // Imagen representativa de animales domésticos
    )
    val animalesDeGranja = listOf(
        "Animales de Granja" to R.drawable.animales_granja // Imagen representativa de animales de granja
    )

    var showDialog by remember { mutableStateOf<String?>(null) }

    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        items(animalesDomesticos) { (imageResId) ->
            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                animalesDomesticos.forEach { (categoria, imageResId) ->
                    // Sección para animales domésticos
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(bottom = 16.dp)
                    ) {
                        // Imagen representativa de la categoría
                        Image(
                            painter = painterResource(id = imageResId),
                            contentDescription = "Imagen de $categoria",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(250.dp)
                                .padding(bottom = 8.dp)
                        )
                        // Nombre de la categoría
                        Text(text = categoria, fontSize = 22.sp, modifier = Modifier.padding(8.dp))
                        // Botón para mostrar las recomendaciones generales
                        Button(
                            onClick = {
                                showDialog = "domesticos"
                            }, // Mostrar el AlertDialog para animales domésticos
                            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                        ) {
                            Text(text = "Recomendaciones Generales")
                        }
                        // Botón para navegar a la pantalla de animales domésticos
                        Button(
                            onClick = { navController.navigate("animales_domesticos") }, // Navega a la pantalla con perros, gatos, etc.
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = "Ver Animales")
                        }
                    }
                }
                animalesDeGranja.forEach { (categoria, imageResId) ->
                    // Sección para animales de granja
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(bottom = 16.dp)
                    ) {
                        // Imagen representativa de la categoría
                        Image(
                            painter = painterResource(id = imageResId),
                            contentDescription = "Imagen de $categoria",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(250.dp)
                                .padding(bottom = 8.dp)
                        )
                        // Nombre de la categoría
                        Text(text = categoria, fontSize = 22.sp, modifier = Modifier.padding(8.dp))
                        // Botón para mostrar las recomendaciones generales
                        Button(
                            onClick = {
                                showDialog = "granja"
                            }, // Mostrar el AlertDialog para animales de granja
                            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                        ) {
                            Text(text = "Recomendaciones Generales")
                        }
                        // Botón para navegar a la pantalla de animales de granja (por ejemplo)
                        Button(
                            onClick = { navController.navigate("animales_granja") },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = "Ver Animales")
                        }
                    }
                }
                // Cuadro de diálogo para recomendaciones generales
                if (showDialog != null) {
                    val recomendaciones = when (showDialog) {
                        "domesticos" -> "Los animales domésticos requieren atención constante, incluyendo alimentación adecuada, higiene y revisiones médicas."
                        "granja" -> "Los animales de granja necesitan un ambiente saludable y cuidados específicos según el tipo de animal, así como acceso a agua limpia y comida."
                        else -> ""
                    }
                    AlertDialog(
                        onDismissRequest = { showDialog = null },
                        title = { Text(text = "Recomendaciones Generales") },
                        text = { Text(text = recomendaciones) },
                        confirmButton = {
                            Button(onClick = { showDialog = null }) {
                                Text(text = "Cerrar")
                            }
                        }
                    )
                }
            }
        }
    }
}