package com.example.telacadastro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.telacadastro.databinding.ActivityEmergenciaBinding
import com.example.telacadastro.databinding.ActivityMain2Binding
import com.google.firebase.auth.FirebaseAuth

class MainActivity2 : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding:ActivityMain2Binding


    override fun onCreate(savedInstanceState: Bundle?) {
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
}