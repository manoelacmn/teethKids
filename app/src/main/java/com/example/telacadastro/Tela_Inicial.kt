package com.example.telacadastro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.telacadastro.databinding.ActivityTelaInicialBinding
import io.grpc.Internal

private lateinit var binding: ActivityTelaInicialBinding

class Tela_Inicial : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityTelaInicialBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnTelaPerfil.setOnClickListener{btnir ->
            irTelaperfil()
        }




    }

    private fun irTelaperfil(){
        var irPerfil = Intent(this,MainActivity2::class.java)
        startActivity(irPerfil)
    }
}