package com.enma.pawfriends.chat.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class Message(
    val userEmail: String, // Cambia el nombre de la propiedad a userEmail
    val content: String
)

class MessagingViewModel : ViewModel() {
    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages = _messages.asStateFlow()

    fun sendMessage(content: String, userEmail: String) {
        val newMessage = Message(userEmail = userEmail, content = content)
        _messages.value = _messages.value + newMessage
    }
}
