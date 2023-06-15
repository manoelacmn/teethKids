package com.example.telacadastro

import AdapterHistorico
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.telacadastro.databinding.ActivityRecyclerHistoricoBinding
import com.example.telacadastro.model.Historico

class recyclerHistorico : AppCompatActivity() {
    private lateinit var binding:ActivityRecyclerHistoricoBinding
    private var listaHistorico: MutableList<Historico> = mutableListOf()
    private lateinit var AdpterHistorico: AdapterHistorico

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityRecyclerHistoricoBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }
}