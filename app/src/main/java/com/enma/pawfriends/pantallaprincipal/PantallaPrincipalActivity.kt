package com.enma.pawfriends.pantallaprincipal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import com.enma.pawfriends.ui.theme.PawFriendsTheme
import com.enma.pawfriends.navigation.NavManager
import com.enma.pawfriends.viewmodel.LoginViewModel
import com.enma.pawfriends.viewmodel.NotesViewModel

class PantallaPrincipalActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val loginViewModel: LoginViewModel by viewModels()
        val notesViewModel: NotesViewModel by viewModels()
        setContent {
            PawFriendsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavManager(
                        loginViewModel = loginViewModel,
                        notesViewModel = notesViewModel
                    )
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.enma.pawfriends.ui.theme.PawFriendsTheme
import com.enma.pawfriends.viewmodel.NotesViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun PantallaPrincipal(navController: NavController) {
    val drawerState = rememberDrawerState(DrawerValue.Closed) // Controlamos el estado del drawer
    val scope = rememberCoroutineScope()

    // Envolver Scaffold dentro de ModalNavigationDrawer para mostrar el menú lateral
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            // En esta parte definire el contenido de la parte lateral
            Drawer() // Llamamos a la función Drawer() para definir el contenido del menú lateral
        }
    ) {
        Scaffold(
            topBar = { TopBar(scope, drawerState) },
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            // Aquí va el contenido principal de tu pantalla
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
    drawerState: DrawerState // Ahora usamos DrawerState en lugar de ScaffoldState
) {
    SmallTopAppBar( // Usamos SmallTopAppBar en lugar de TopAppBar
        title = { Text(text = "Paw Friends") },
        navigationIcon = {
            IconButton(onClick = {
                scope.launch {
                    drawerState.open() // Abrimos el menú lateral al hacer clic en el ícono de menú
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PantallaPrincipalPreview2() {
    PawFriendsTheme {
    }
}


