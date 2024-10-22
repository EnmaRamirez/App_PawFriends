package com.enma.pawfriends

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.enma.pawfriends.services.FirestoreService
import com.enma.pawfriends.ui.screens.PetRegistrationScreen
import com.enma.pawfriends.ui.theme.PawFriendsTheme

class MainActivity : ComponentActivity() {
    private val firestoreService = FirestoreService()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PawFriendsTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Column {
                        PetRegistrationScreen(firestoreService = firestoreService, ownerId = "owner123")
                        ElevatedButton(
                            onClick = {
                                val intent = Intent(this@MainActivity, ReviewActivity::class.java)
                                startActivity(intent)
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Ir a Review Activity")
                        }
                        Elementos()
                    }
                }
            }
        }
    }
}

@Composable
fun Elementos() {
    val mContext = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logitopaw),
            contentDescription = "Paw Friends"
        )
        Text(
            "Paw Friends",
            color = Color.Blue,
            fontSize = 48.sp
        )
        Text("Bienvenido, Usuario")
        Row {
            OutlinedButton(onClick = { /*TODO*/ }) {
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
fun ElementosPreview() {
    PawFriendsTheme {
        Elementos()
    }
}
