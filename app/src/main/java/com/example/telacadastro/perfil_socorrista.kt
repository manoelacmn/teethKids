package com.example.telacadastro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.telacadastro.databinding.ActivityCriarcontaBinding
import com.example.telacadastro.databinding.ActivityPerfilSocorristaBinding

class perfil_socorrista : AppCompatActivity() {

    private lateinit var binding: ActivityPerfilSocorristaBinding

    companion object{
        const val  LETTER = "latter"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val letterId = intent?.extras?.getString("LETTER").toString()
        binding = ActivityPerfilSocorristaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //binding.tvNome.setText("adicionar o id do usuarioa aqui")


    }
}