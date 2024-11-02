package com.enma.pawfriends

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.enma.pawfriends.R
import com.enma.pawfriends.navigation.NavManager
import com.enma.pawfriends.repuesto.viewmodel.LoginViewModel
import com.enma.pawfriends.repuesto.viewmodel.NotesViewModel
import com.enma.pawfriends.services.FirestoreService
import com.enma.pawfriends.ui.theme.PawFriendsTheme


import com.enma.pawfriends.navigation.NavManager
import com.enma.pawfriends.ui.theme.PawFriendsTheme
import com.enma.pawfriends.viewmodel.LoginViewModel
import com.enma.pawfriends.viewmodel.NotesViewModel
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import android.util.Log

class MainActivity : ComponentActivity() {
    private val firestoreService = FirestoreService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)

        // Obtener token FCM
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val token = task.result
                Log.d("FCM Token", "Token: $token")
                // Puedes guardar el token en tu backend o Firestore
            } else {
                Log.e("FCM Token", "Error al obtener el token", task.exception)
            }
        }

        val loginViewModel: LoginViewModel by viewModels()
        val notesViewModel: NotesViewModel by viewModels()

        setContent {
            PawFriendsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavManager(loginViewModel = loginViewModel, notesViewModel = notesViewModel)
                }
            }
        }
    }
}

@Composable
fun Elementos(navController: NavController) {
    val mContext = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {
        Box (
            modifier = Modifier
                .clip(shape = MaterialTheme.shapes.small)
                .background(MaterialTheme.colorScheme.secondary)
                .border(2.dp, MaterialTheme.colorScheme.primary)
        ){
            Image(
                painter = painterResource(id = R.drawable.logitopaw),
                contentDescription = "Paw Frieds",
                modifier = Modifier
                    .padding(20.dp)
            )
        }

        Text(
            "Paw Friends",
            color = MaterialTheme.colorScheme.primaryContainer,
            style = MaterialTheme.typography.bodyMedium
        )
        Text("Bienvenido, Usuario",
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.bodySmall
        )
        Row() {
            OutlinedButton(onClick = {/*TODO*/ }) {
                Text("Tu mascota")
            }
            Spacer(modifier = Modifier.width(10.dp))
            OutlinedButton(
                onClick = {
                    navController.navigate("login")

                }
            ) {
                Text("Inicia sesion")
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun ElementosPreview(){
    PawFriendsTheme{
        // Elementos()
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
