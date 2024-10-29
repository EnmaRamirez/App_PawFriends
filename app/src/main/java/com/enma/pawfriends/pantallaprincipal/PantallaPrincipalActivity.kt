package com.enma.pawfriends.pantallaprincipal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.enma.pawfriends.ui.theme.PawFriendsTheme
import com.enma.pawfriends.navigation.NavManager
import com.enma.pawfriends.view.login.LoginView
import com.enma.pawfriends.view.notas.HomeView
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
                    //LoginView()
                }
            }
        }
    }
}

/*@Composable
fun LoginView(){

}*/


