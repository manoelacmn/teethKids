package com.example.telacadastro

import AdapterHistorico
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.telacadastro.adapter.AdapterAvaliacao
import com.example.telacadastro.databinding.ActivityRecyclerHistoricoBinding
import com.example.telacadastro.model.Historico
import com.example.telacadastro.model.avaliacao

class recyclerHistorico : AppCompatActivity() {

    private var listaHistorico: MutableList<Historico> = mutableListOf()
    private lateinit var AdpterHistorico: AdapterHistorico
    private lateinit var binding:ActivityRecyclerHistoricoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecyclerHistoricoBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.recycleHistorico.layoutManager =
            LinearLayoutManager(this)
        binding.recycleHistorico.setHasFixedSize(true)
        AdpterHistorico= AdapterHistorico(this,listaHistorico)
        binding.recycleHistorico.adapter = AdpterHistorico
        itens()
}
    private fun itens(){
        val nome =  Historico("pereira","quebrou o dente fazendo capoeira","10/05/2001-22:53")
        listaHistorico.add(nome)
    }
}