package com.example.telacadastro.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.example.telacadastro.R
import com.example.telacadastro.model.avaliacao
import com.example.telacadastro.recycleravaliacao

class AdapterAvaliacao(private val context: Context, private val avaliacao:MutableList<avaliacao>):
        RecyclerView.Adapter<AdapterAvaliacao.avaliacaoViewHolder>() {
                inner class avaliacaoViewHolder(itemView: android.view.View): RecyclerView.ViewHolder(itemView){
                        val nome: TextView = itemView.findViewById<TextView>(R.id.nome)
                        val rating: RatingBar = itemView.findViewById(R.id.rating)

                }


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): avaliacaoViewHolder {
                val itemList = LayoutInflater.from(context).inflate(R.layout.avaliacoes,parent,false)
                val holder = avaliacaoViewHolder(itemList)
                return  holder
        }

        override fun getItemCount(): Int {
                return avaliacao.size
        }

        override fun onBindViewHolder(holder: avaliacaoViewHolder, position: Int) {
                TODO("Not yet implemented")
        }
}



