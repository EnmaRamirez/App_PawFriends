package com.enma.pawfriends.view.notas

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmarks
//import androidx.compose.material.icons.filled.CrueltyFree
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Pets
//import androidx.compose.material.icons.filled.People
//import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.TipsAndUpdates
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ControlledComposition
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.enma.pawfriends.R
import com.enma.pawfriends.model.DrawerMenuItem
import com.enma.pawfriends.viewmodel.NotesViewModel
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
                    painter = painterResource(id = R.drawable.imagenlateral), // Cambia esto por el nombre de tu imagen
                    contentDescription = "Cabecera del menú",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp) // Ajusta la altura según sea necesario
                )
                Text("Menu", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(16.dp))

                // Menú lateral con opciones aqui llamo todas mis funciones
                val menuItems = listOf(
                    DrawerMenuItem("Registro de Mascotas", icon = painterResource(id = R.drawable.registrosmascotas), route = "register_pet"),
                    DrawerMenuItem("Reporte de mascotas pedidas y encontradas", icon = painterResource(id = R.drawable.reporte), route = "pet_reports"),
                    DrawerMenuItem("Consejos de cuidados y recursos veterinarios", icon = painterResource(id = R.drawable.saludveterinario), "pantalla_inicial"),
                    DrawerMenuItem("Servicios de mascotas", icon = painterResource(id = R.drawable.registrosmascotas), route = "pantallaInicial")

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
                            navController.navigate("login") // Navegar a la pantalla de login al cerrar sesión
                        }) {
                            Icon(imageVector = Icons.Default.ExitToApp, contentDescription = "Sign Out")
                        }

                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary, // Cambia por el color que quieras
                        titleContentColor = MaterialTheme.colorScheme.onPrimary // Color del título
                    )

                )
            },//Aqui agrego la barra laterar inferior
            bottomBar = {
                BottomAppBar (
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White,

                ){
                    Row (
                        modifier = Modifier.fillMaxWidth()
                    ){
                        IconButton(onClick = {navController.navigate("inicio")},
                            modifier = Modifier.weight(1f)) {
                            Icon(imageVector = Icons.Default.Home, contentDescription = "Inicio", modifier = Modifier.size(30.dp) )
                        }
                        IconButton(onClick = {navController.navigate("consejos")},
                            modifier = Modifier.weight(1f)) {
                            Icon(imageVector = Icons.Default.Pets, contentDescription = "Consejos", modifier = Modifier.size(30.dp))
                        }
                        IconButton(onClick = {navController.navigate("mensajeria") },
                            modifier = Modifier.weight(1f)) {
                            Icon(imageVector = Icons.Default.Message, contentDescription = "Mensajeria", modifier = Modifier.size(30.dp))
                        }
                    }

                }
            }
        ) { innerPadding ->
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 100.dp)
            ){
                Column (
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Text(text = "Home")
                    //Aqui puedo agregar mas contenido a mi vista principal
                }//Funcion 6
                FloatingActionButton(
                    onClick = {navController.navigate("servicios")},
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(20.dp)
                ) {
                    Icon(imageVector = Icons.Default.TipsAndUpdates, contentDescription =  "Servicios")
                }
            }
        }
    }
}