package com.example.telacadastro

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.telacadastro.databinding.ActivityTeladeperfilBinding

class TelaPerfil:AppCompatActivity() {

    private lateinit var binding: ActivityTeladeperfilBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTeladeperfilBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}