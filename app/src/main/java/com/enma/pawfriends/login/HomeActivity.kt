package com.enma.pawfriends.login

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.enma.pawfriends.R
import com.google.firebase.auth.FirebaseAuth

enum class ProviderType {
    BASIC
}

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        //Setup
        val bundle = intent.extras
        val email = bundle?.getString("email")
        val provider = bundle?.getString("provider")
        setup(email ?:"", provider ?:"")
    }
    private fun setup(email: String, provider: String){
        title = "Inicio"

        setContentView(R.layout.activity_home)  // Asegúrate de que este es el layout correcto

        // Asigna las vistas a las variables correspondientes
        val emailTextView = findViewById<TextView>(R.id.emailTextView)
        val providerTextView = findViewById<TextView>(R.id.providerTextView)
        val logOutButton = findViewById<Button>(R.id.logOutButton)



        emailTextView.text = email
        providerTextView.text = provider

        logOutButton.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            onBackPressed()
        }




    }

}