package com.enma.pawfriends.view.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth


//ESTA FUNCION NOS AYUDA A CONTROLAR LA PANTALLA QUE SI YA INIAMOS CECION NOS DIRIJA A LA PANTALLA HOME
@Composable
fun BlanckView(navController: NavController){
    LaunchedEffect(Unit) {
        if(!FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty()){
            navController.navigate("Home")
        }else{
            navController.navigate("login")
        }
    }

}