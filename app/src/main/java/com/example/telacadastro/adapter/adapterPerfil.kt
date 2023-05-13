package com.example.telacadastro.adapter
// no adapter vai erdar as caracteriscias do recycle View

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.telacadastro.R
import com.example.telacadastro.model.perfil

class  adpterPerfil(private val context: Context,private val perfil:MutableList<perfil>): RecyclerView.Adapter<adpterPerfil.PerfilViewHolder>(){
    //esse adapter espera o viewholder -> os itens de cada lista
    inner class PerfilViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val foto = itemView.findViewById<ImageView>(R.id.imgFotoPerfil) // recuperando a view do layout
        val nome = itemView.findViewById<TextView>(R.id.nomeUsuario)
        val descrição = itemView.findViewById<TextView>(R.id.descrição)
        val analisar = itemView.findViewById<Button>(R.id.BtnAnalisar)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PerfilViewHolder {
        //Responsavel por criar as vizualizações da lista
        val itemLIsta = LayoutInflater.from(context).inflate(R.layout.perfil_cliente,parent,false)//nao usa o root
        //.from() indica o contexto que esta passndo esse inflate
        val holder = PerfilViewHolder(itemLIsta)
        return holder // criado a vizualiçaõ
    }



    override fun onBindViewHolder(holder: PerfilViewHolder, position: Int) {
        // esse metodo cria a vizualição dos view que foram criados
        holder.foto.setImageResource(perfil[position].fot)
        holder.nome.text = perfil[position].nome
        holder.descrição.text=perfil[position].Descrição
        holder.analisar.text=perfil[position].analisar

    }
    override fun getItemCount(): Int = perfil.size
        //ele é responsavel pelo tamanho da lista pela quantidade de itens nela
}
//inner é uma classe interna
//é necessario criar o construtor da classe RecycleView.ViewHolder -> o iten view
//é necessario criar Metodos --onCreatViewHolder
//                              onBindViewHolder
//                              getItemcout