package com.example.telacadastro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.telacadastro.databinding.ActivityEmergenciaBinding
import com.example.telacadastro.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {
    private lateinit var binding:ActivityMain2Binding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.BtnEmergencia.setOnClickListener { btnemerg -> iremergencia() }
    }



    private fun iremergencia(){
        var irParaEmerg = Intent(this,Emergencia::class.java)
        startActivity(irParaEmerg)
    }
}