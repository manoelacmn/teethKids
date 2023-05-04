package com.example.telacadastro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.telacadastro.databinding.ActivityEmergenciaBinding
import com.example.telacadastro.databinding.ActivityMain2Binding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity2 : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding:ActivityMain2Binding


    override fun onCreate(savedInstanceState: Bundle?) {

        val user = Firebase.auth.currentUser
        user?.let {
            // Name, email address, and profile photo Url
            val name = it.displayName
            val email = it.email
            val photoUrl = it.photoUrl

            // Check if user's email is verified
            val emailVerified = it.isEmailVerified

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            val uid = it.uid
        }
        if (user != null) {
            Toast.makeText(
                baseContext,
                user.uid,
                Toast.LENGTH_SHORT,
            ).show()

        }
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.switch1.setOnCheckedChangeListener{buttonView,isCheked -> isCheked
        var ligadoOuDesligado = ""
        if (isCheked) {
            ligadoOuDesligado = "Ligado"

            val status = binding.switch1.text.toString()
            statustperfil(status)

            Toast.makeText(
                baseContext,
                "Online",
                Toast.LENGTH_SHORT,
            ).show()
        }else {
            ligadoOuDesligado="Desligado"
            Toast.makeText(
                baseContext,
                "Offline",
                Toast.LENGTH_SHORT,
            ).show()
        }
        binding.switch1.text = ligadoOuDesligado
        }
    }

    private fun statustperfil(status:String) {
    }

    private fun iremergencia(){
        var irParaEmerg = Intent(this,Emergencia::class.java)
        startActivity(irParaEmerg)
    }
    companion object {

        private val TAG = "myProfile"

    }
}