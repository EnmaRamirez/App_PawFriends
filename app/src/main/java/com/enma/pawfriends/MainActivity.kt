package com.enma.pawfriends

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
        setContent {
            PawFriendsTheme {
                Surface(modifier = androidx.compose.ui.Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background){
                }
                Elementos()

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