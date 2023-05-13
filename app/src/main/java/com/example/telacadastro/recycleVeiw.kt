package com.example.telacadastro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.telacadastro.adapter.adpterPerfil
import com.example.telacadastro.databinding.ActivityRecycleVeiwBinding
import com.example.telacadastro.model.perfil

private lateinit var binding: ActivityRecycleVeiwBinding
class recycleVeiw : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecycleVeiwBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)//definindo em qual posicção vai aparecer as views,por padrao já vem na vertical
        binding.recyclerView.setHasFixedSize(true)//como verdadeiro, da mais desenpenho na reciclagem da lista
        // configurando o adapter
        val listadePerfil : MutableList<perfil> = mutableListOf()
        val adapterPerfil=adpterPerfil(this,listadePerfil)//o construtor espera os parametros  da classe adpeterperfil lista
        binding.recyclerView.adapter = adapterPerfil
        //criando os itens da lista
        val perfeil1 = perfil(
            R.drawable.baseline_person_24,
            "Nome do usuario",
            "Distância,Tempo de Espera",
            "Analisar"
        )
        // adicionar na lista d chamada(perfil)
        listadePerfil.add(perfeil1)

    }
}