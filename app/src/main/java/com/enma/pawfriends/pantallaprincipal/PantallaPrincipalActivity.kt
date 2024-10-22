package com.enma.pawfriends.pantallaprincipal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.enma.pawfriends.ui.theme.PawFriendsTheme
import com.enma.pawfriends.viewmodel.NotesViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import com.enma.pawfriends.Destinos.*
import com.enma.pawfriends.services.FirestoreService

class PantallaPrincipalActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PawFriendsTheme {
            }
        }
    }
}
@Composable
fun PantallaPrincipal(navController: NavController,
                      viewModel: NotesViewModel
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed) // Controlamos el estado del drawer
    val scope = rememberCoroutineScope()

    val firestoreService = FirestoreService()
    val ownerId = "usuarios123"
    val navigationItems = listOf(
        Pantalla1,
        Pantalla2,
        Pantalla3,
        Pantalla4,
        Pantalla5,
        Pantalla6,
        Pantalla7,
        Pantalla8,
        Pantalla9
    )

    // Envolver Scaffold dentro de ModalNavigationDrawer para mostrar el menú lateral
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            // En esta parte definire el contenido de la parte lateral
            Drawer(
                scope,
                drawerState,
                navController,
                menuItems = navigationItems) // Llamamos a la función Drawer() para definir el contenido del menú lateral
        }
    ) {

        Scaffold(
            topBar = { TopBar(scope, drawerState) },
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->

            NavigationHost(navController = navController, firestoreService = firestoreService, ownerId = ownerId)

            // Aquí va el contenido de la pantalla principal
            Text(
                text = "Contenido principal",
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    scope: CoroutineScope,
    drawerState: DrawerState // Reemplazo de DrawerState por ScaffoldState
) {
    TopAppBar(
        title = { Text(text = "Paw Friends") },
        navigationIcon = {
            IconButton(onClick = {
                scope.launch {
                    drawerState.open() // Abrimos el menú lateral al hacer clic en el ícono de menú
                }
            }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Icono de menú"
                )
            }
        }
    )
}

@Composable
fun Drawer(
    scope: CoroutineScope,
    drawerState: DrawerState,
    navController: NavController,

    menuItems : List<Destinos>) {
    /*val menuItems = listOf(
        "Registro y gestión de mascotas",
        "Reporte de mascotas perdidas y encontradas",
        "Adopciones de mascotas",
        "Servicios de mascotas",
        "Consejos de cuidados y recursos veterinarios",
        "Mensajería directa",
        "Notificaciones",
        "Calificación y reseñas",
        "Recompensas y reconocimientos"
    )*/
    Column() {
        Image(
            painterResource(id = R.drawable.imagenlateral),
            contentDescription = "Menu de opciones",
            modifier = Modifier
                .height(160.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(15.dp)
        )
        val currentRoute = currentRoute(navController)

        menuItems.forEach { item ->
            DrawerItem(item = item,
                selected = currentRoute == item.ruta){
                navController.navigate(item.ruta){
                    launchSingleTop = true
                }
                scope.launch {
                    drawerState.close()

                }
            }
        }
    }
}

@Composable
fun DrawerItem(item: Destinos,
               selected: Boolean,
               onItemClick: (Destinos)->Unit
){
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(6.dp)
            .clip(RoundedCornerShape(12))
            .background(if(selected) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.25f)
            else Color.Transparent)
            .padding(8.dp)
            .clickable { onItemClick(item) },
        verticalAlignment = Alignment.CenterVertically

    ){
        Image(painterResource(id = item.icon),
            contentDescription = item.title)
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = item.title,
            style =  MaterialTheme.typography.bodySmall,
            color = if (selected) MaterialTheme.colorScheme.secondary
            else MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun currentRoute(navController: NavController): String?{
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}
@Preview(showBackground = true)
@Composable
fun PantallaPrincipalPreview2() {
    PawFriendsTheme {
    }
}



