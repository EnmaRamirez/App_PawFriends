package com.enma.pawfriends.view.notas

import android.util.Log // Importación correcta para utilizar Log.e()
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.enma.pawfriends.R
import com.enma.pawfriends.model.DrawerMenuItem
import com.enma.pawfriends.repuesto.viewmodel.NotesViewModel

import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(navController: NavController, viewModel: NotesViewModel) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Image(
                    painter = painterResource(id = R.drawable.imagenlateral),
                    contentDescription = "Cabecera del menú",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
                Text("Menú", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(16.dp))

                // Menú lateral con opciones aquí llamo todas mis funciones
                val menuItems = listOf(
                    DrawerMenuItem("Mi Perfil", icon = painterResource(id = R.drawable.ic_user_profile), route = "userProfile"),
                    DrawerMenuItem("Registro de Mascotas", icon = painterResource(id = R.drawable.registrosmascotas), route = "register_pet"),
                    DrawerMenuItem("Reporte de Mascotas Perdidas y Encontradas", icon = painterResource(id = R.drawable.reporte), route = "pet_reports"),
                    DrawerMenuItem("Consejos de Cuidados y Recursos Veterinarios", icon = painterResource(id = R.drawable.saludveterinario), route = "pantalla_inicial"),
                    DrawerMenuItem("Clasificación", icon = painterResource(id = R.drawable.ic_leaderboard), route = "leaderboard"),
                    DrawerMenuItem("Calificaciones y Reseñas", icon = painterResource(id = R.drawable.ic_reviews), route = "review_screen") // Agregar sistema de reseñas
                    DrawerMenuItem("Reporte de mascotas perdidas y encontradas", icon = painterResource(id = R.drawable.reporte), route = "pet_reports"),
                    DrawerMenuItem("Consejos de cuidados y recursos veterinarios", icon = painterResource(id = R.drawable.saludveterinario), route = "pantalla_inicial"),
                    DrawerMenuItem("servicios de Mascotas", icon = painterResource(id = R.drawable.registrosmascotas), route = "StartScreen")
                )

                menuItems.forEach { item ->
                    NavigationDrawerItem(
                        label = { Text(item.title) },
                        icon = { Image(painter = item.icon, contentDescription = item.title, modifier = Modifier.size(40.dp)) },
                        selected = false,
                        onClick = {
                            try {
                                navController.navigate(item.route) {
                                    launchSingleTop = true
                                    restoreState = true
                                }
                                scope.launch { drawerState.close() }
                            } catch (e: Exception) {
                                // Manejar error de navegación
                                Log.e("NavigationError", "Error al navegar a la ruta: ${item.route}", e)
                            }
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Paw Friends") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(imageVector = Icons.Default.Menu, contentDescription = "Menú")
                        }
                    },
                    actions = {
                        IconButton(onClick = {
                            viewModel.signOut()
                            navController.navigate("login") {
                                popUpTo("login") { inclusive = true }
                            } // Navegar a la pantalla de login al cerrar sesión
                            navController.navigate("login")
                        }) {
                            Icon(imageVector = Icons.Default.ExitToApp, contentDescription = "Cerrar Sesión")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary
                    )
                )

            },
            // Barra inferior
            },
            bottomBar = {
                BottomAppBar(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White,
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        IconButton(
                            onClick = {
                                navController.navigate("inicio") {
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(imageVector = Icons.Default.Home, contentDescription = "Inicio", modifier = Modifier.size(30.dp))
                        }
                        IconButton(
                            onClick = {
                                navController.navigate("consejos") {
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(imageVector = Icons.Default.Pets, contentDescription = "Consejos", modifier = Modifier.size(30.dp))
                        }
                        IconButton(
                            onClick = {
                                navController.navigate("mensajeria") {
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(imageVector = Icons.Default.Message, contentDescription = "Mensajería", modifier = Modifier.size(30.dp))
                    Row(modifier = Modifier.fillMaxWidth()) {
                        IconButton(onClick = { navController.navigate("inicio") },
                            modifier = Modifier.weight(1f)) {
                            Icon(imageVector = Icons.Default.Home, contentDescription = "Inicio", modifier = Modifier.size(30.dp))
                        }
                        IconButton(onClick = { navController.navigate("consejos") },
                            modifier = Modifier.weight(1f)) {
                            Icon(imageVector = Icons.Default.Favorite, contentDescription = "Consejos", modifier = Modifier.size(30.dp))
                        }
                        IconButton(onClick = { navController.navigate("mensajeria") },
                            modifier = Modifier.weight(1f)) {
                            Icon(imageVector = Icons.Default.Email, contentDescription = "Mensajeria", modifier = Modifier.size(30.dp))
                        }
                    }
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 100.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Inicio")
                    // Aquí puedo agregar más contenido a mi vista principal
                    Text(text = "Home")
                }
                FloatingActionButton(
                    onClick = { navController.navigate("servicios") },
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(20.dp)
                ) {
                    Icon(imageVector = Icons.Default.TipsAndUpdates, contentDescription = "Servicios")
                    Icon(imageVector = Icons.Default.Settings, contentDescription = "Servicios")
                }
            }
        }
    }
}
