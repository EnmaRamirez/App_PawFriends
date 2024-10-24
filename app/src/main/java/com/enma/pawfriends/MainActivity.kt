package com.enma.pawfriends

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import com.enma.pawfriends.ui1.MessagingScreen
import com.google.firebase.auth.FirebaseAuth
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.enma.pawfriends.navigation.NavManager
import com.enma.pawfriends.services.FirestoreService
import com.enma.pawfriends.ui.theme.PawFriendsTheme


class MainActivity : ComponentActivity() {
    private val firestoreService = FirestoreService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Solicitar permiso de notificación si es Android 13 o superior
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    1
                )
            }
        }
        setContent {
            AppNavigation()
        }
    }
        setContent {
            PawFriendsTheme {
                Surface(modifier = androidx.compose.ui.Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background){
                }
                Elementos()

    @Composable
    fun AppNavigation() {
        val auth = FirebaseAuth.getInstance()
        var isAuthenticated by remember { mutableStateOf(auth.currentUser != null) }

        if (isAuthenticated) {
            // Si el usuario está autenticado, muestra la pantalla de perfil
            ProfileScreen()
        } else {
            // Si el usuario no está autenticado, muestra la pantalla de autenticación
            AuthScreen(onAuthSuccess = {
                isAuthenticated = true
            })
        }
    }

    @Composable
    fun ProfileScreen() {

        val context = LocalContext.current

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            Button(
                onClick = {
                    val intent = Intent(context, MessagingActivity::class.java)
                    context.startActivity(intent)
                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(text = "Ir a Mensajería")
            }
        }
    }
}
@Composable
fun AuthScreen(onAuthSuccess: () -> Unit) {
    // Implementación básica de la pantalla de autenticación
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Button(onClick = {
            // Aquí iría la lógica de autenticación
            onAuthSuccess()
        }) {
            Text(text = "Iniciar sesión")
        }
    }
}

class MessagingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MessagingScreen(navController = rememberNavController())
            }
        }
}








fun Elementos() {
    val mContext = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {
        Image(
            painter = painterResource(id = R.drawable.logitopaw),
            contentDescription = "Paw Frieds"
        )
        Text(
            "Paw Friends",
            color = Color.Blue,
            fontSize = 48.sp
        )
        Text("Bienvenido, Usuario")
        Row() {
            OutlinedButton(onClick = {/*TODO*/ }) {
                Text("Tu mascota")
            }
            Spacer(modifier = Modifier.width(10.dp))
            OutlinedButton(
                onClick = {
                    mContext.startActivity(Intent(mContext, PantallaPrincipalActivity::class.java))

                }
            ) {
                Text("Contenidos")
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun ElementosPreview(){
    PawFriendsTheme{
        Elementos()
    }
}

/*
@Composable
fun Greeting(name: String){
    Text(text = "Hello $name")

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview(){
    PawFriendsTheme{
        Greeting("Android")
    }
}
*/
