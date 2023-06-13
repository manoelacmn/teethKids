package com.example.telacadastro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.telacadastro.databinding.ActivityTelaInicialBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import io.grpc.Internal


private lateinit var binding: ActivityTelaInicialBinding

class Tela_Inicial : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        auth = Firebase.auth
        val user = Firebase.auth.currentUser
        user?.let {
            val name = it.displayName
            val email = it.email
            val photoUrl = it.photoUrl

            // Check if user's email is verified
//            val emailVerified = it.isEmailVerified

            val uid = it.uid
        }
        super.onCreate(savedInstanceState)
        binding= ActivityTelaInicialBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnTelaPerfil.setOnClickListener{btnir ->
            irTelaperfil()
        }
        binding.btnTelaEmergencia.setOnClickListener { btnir ->
            emergencia()
        }
        binding.btnConfiguraO.setOnClickListener({btn -> tirarFoto()})




    }

    private fun irTelaperfil(){
        var irPerfil = Intent(this,MainActivity2::class.java)
        startActivity(irPerfil)
    }
    private fun emergencia(){
        var irEmergencia = Intent(this,recycleVeiw::class.java)
        startActivity(irEmergencia)
    }
    private fun tirarFoto(){
        var intent=Intent(this,Emergencia::class.java)
        startActivity(intent)
    }
}