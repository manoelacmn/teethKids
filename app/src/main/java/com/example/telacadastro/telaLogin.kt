package com.example.telacadastro

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.telacadastro.databinding.TelaLoginBinding

class telaLogin:AppCompatActivity() {

    private lateinit var binding: TelaLoginBinding


    override fun onCreate(savedInstanceState : Bundle?){
        super.onCreate(savedInstanceState)
        binding = TelaLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cadastro.setOnClickListener{tvcadastro-> ircadastro()}

        binding.BtnEntar.setOnClickListener {btn -> irTelaPerfil()}
    }






    private fun ircadastro(){
        var telacadastro = Intent(this,criarConta::class.java)
        startActivity(telacadastro)
    }


    private fun irTelaPerfil(){
        var telaperfil = Intent(this,MainActivity2::class.java)
        startActivity(telaperfil)
    }

}