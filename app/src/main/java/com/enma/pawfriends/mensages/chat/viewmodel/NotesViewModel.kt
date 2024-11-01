package com.enma.pawfriends.chat.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class NotesViewModel: ViewModel(){
    private val auth: FirebaseAuth = Firebase.auth

    fun signOut(){
        auth.signOut()
    }
}