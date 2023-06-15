package com.example.telacadastro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.telacadastro.databinding.ActivitySegundaConfirmacaoBinding

private lateinit var binding:ActivitySegundaConfirmacaoBinding
class segundaConfirmacao : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySegundaConfirmacaoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnEnviar.setOnClickListener{btn->

        }
    }
}