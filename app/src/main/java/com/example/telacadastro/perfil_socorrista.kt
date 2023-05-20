package com.example.telacadastro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class perfil_socorrista : AppCompatActivity() {
    companion object{
        const val  LETTER = "latter"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil_socorrista)
        val letterId = intent?.extras?.getString("LETTER").toString()
    }
}