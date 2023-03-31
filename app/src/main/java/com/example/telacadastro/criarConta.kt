package com.example.telacadastro

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.example.telacadastro.databinding.ActivityCriarcontaBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase



class criarConta: AppCompatActivity() {

    private lateinit var binding: ActivityCriarcontaBinding

    private fun hideSoftKeyboard(){
        val softKeyManager =
            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        softKeyManager.hideSoftInputFromWindow(binding.BtnCriarConta.windowToken, 0)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCriarcontaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.BtnCriarConta.setOnClickListener { btnCriarConta ->
            hideSoftKeyboard()
            val email = binding.EtEmailCriarConta.text.toString()
            val senha = binding.EtSenhaCriarConta.text.toString()
            val nome = binding.EtNomeCriarConta.text.toString()
            val endereco = binding.EtEnderecoCriarConta.text.toString()
            val cv = binding.EtCurriculoCriarConta.text.toString()


            val db = Firebase.firestore
            val usuario = hashMapOf(
                "email" to email,
                "senha" to senha,
                "nome" to nome,
                "endereco" to endereco,
                "curriculo" to cv,
            )
            db.collection("usuarios").add(usuario)
            irParaTelaLogin()
        }
    }


    private fun irParaTelaLogin(){
        var telaLog = Intent(this,telaLogin::class.java)
        startActivity(telaLog)
    }
}

