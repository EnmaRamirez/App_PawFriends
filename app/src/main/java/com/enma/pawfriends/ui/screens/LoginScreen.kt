package com.enma.pawfriends.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.enma.pawfriends.viewmodel.AuthViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*



@Composable
fun LoginScreen(navController: NavController, authViewModel: AuthViewModel){
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column (
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center
    ){
        TextField(
            value = email,
            onValueChange = {email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()


        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = password,
            onValueChange = {password = it},
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()

        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                authViewModel.loginUser(
                    email,
                    password,
                    onSuccess = {
                        navController.navigate("HomeScrenn")
                    },
                    onFailure = {
                        errorMessage = it.message
                    }
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }
        errorMessage?.let{
            Spacer(modifier = Modifier.height(8.dp))
            Text(it, color = MaterialTheme.colorScheme.error)
        }
    }
}









