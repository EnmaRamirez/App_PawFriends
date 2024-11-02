package com.enma.pawfriends.pantallaprincipal

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.enma.pawfriends.R
import com.enma.pawfriends.viewmodel.NotesViewModel

@Composable
fun Elementos(navController: NavController
) {
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
                    navController.navigate("pantalla_principal")

                }
            ) {
                Text("Contenidos")
            }
        }
    }

}