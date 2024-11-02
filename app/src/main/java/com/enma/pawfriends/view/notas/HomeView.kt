package com.enma.pawfriends.view.notas

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
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
                Text("Menu", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(16.dp))

                // Menú lateral con opciones aquí llamo todas mis funciones
                val menuItems = listOf(
                    DrawerMenuItem("Registro de Mascotas", icon = painterResource(id = R.drawable.registrosmascotas), route = "register_pet"),
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
                            navController.navigate(item.route)
                            scope.launch { drawerState.close() }
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
                            Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                        }
                    },
                    actions = {
                        IconButton(onClick = {
                            viewModel.signOut()
                            navController.navigate("login")
                        }) {
                            Icon(imageVector = Icons.Default.ExitToApp, contentDescription = "Sign Out")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            },
            bottomBar = {
                BottomAppBar(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White,
                ) {
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
                    Text(text = "Home")
                }
                FloatingActionButton(
                    onClick = { navController.navigate("servicios") },
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(20.dp)
                ) {
                    Icon(imageVector = Icons.Default.Settings, contentDescription = "Servicios")
                }
            }
        }
    }
}