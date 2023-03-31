package com.example.telacadastro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.telacadastro.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.BtnEntrar.setOnClickListener { bentrar ->
            irParaTelaLogin()
        }
        binding.BtnCriarConta.setOnClickListener { bcriarconta ->
            irParaCriarConta()
        }
    }


    private  fun irParaCriarConta(){
        var criarcontaTela = Intent(this,criarConta::class.java)
        startActivity(criarcontaTela)
    }
    private fun irParaTelaLogin(){
        var telaLog = Intent(this,telaLogin::class.java)
        startActivity(telaLog)
    }
}