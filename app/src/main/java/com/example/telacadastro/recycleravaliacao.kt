package com.example.telacadastro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.telacadastro.databinding.ActivityRecycleravaliacaoBinding

private lateinit var binding: ActivityRecycleravaliacaoBinding
//private  var avaliacao:MutableList<avaliacao>=mutableListOf()
//private lateinit var adpterAvaliacao:AdpterAvaliacao

class recycleravaliacao : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityRecycleravaliacaoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recycleAvaliacao.layoutManager=
            LinearLayoutManager(this)
        binding.recycleAvaliacao.setHasFixedSize(true)

    }
}