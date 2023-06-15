package com.example.telacadastro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.telacadastro.adapter.AdapterAvaliacao
import com.example.telacadastro.databinding.ActivityRecycleravaliacaoBinding
import com.example.telacadastro.model.avaliacao

class recycleravaliacao : AppCompatActivity() {


    private lateinit var binding: ActivityRecycleravaliacaoBinding
    private  var listaavaliacao:MutableList<avaliacao> = mutableListOf()
    private lateinit var AdpterAvaliacao:AdapterAvaliacao


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityRecycleravaliacaoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recycleAvaliacao.layoutManager=
            LinearLayoutManager(this)
        binding.recycleAvaliacao.setHasFixedSize(true)

        AdpterAvaliacao= AdapterAvaliacao(this,listaavaliacao)
        binding.recycleAvaliacao.adapter = AdpterAvaliacao
        itens()
        itens2()

    }
    private fun itens(){
        val nome =  avaliacao("pereira",2.5,"atendimento merda")
        listaavaliacao.add(nome)
    }
    private fun itens2(){
        val nome =  avaliacao("washinton",4.5,"super bacana, bleo serviço, belo profissional, belo homem")
        listaavaliacao.add(nome)
    }
}