package com.example.telacadastro.adapter
// no adapter vai erdar as caracteriscias do recycle View

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.telacadastro.R
import com.example.telacadastro.model.perfil
import com.example.telacadastro.perfil_socorrista
import com.example.telacadastro.recycleVeiw

class  AdpterPerfil(private  val context: Context,private val perfil: MutableList<perfil>,val listener:Myclicklistenner ): RecyclerView.Adapter<AdpterPerfil.PerfilViewHolder>(){
    //esse adapter espera o viewholder -> os itens de cada lista
    inner class PerfilViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val foto = itemView.findViewById<ImageView>(R.id.imgFotoPerfil) // recuperando a view do layout
        val nome: TextView = itemView.findViewById<TextView>(R.id.nomeUsuario)
        val descrição = itemView.findViewById<TextView>(R.id.descrição)
        val uid: TextView = itemView.findViewById<TextView>(R.id.uid)
        val imagePath = itemView.findViewById<TextView>(R.id.imagePath)
        init {
            itemView.setOnClickListener{
                val position = adapterPosition
                Log.d("NOME Clicked:",nome.text.toString())
                Log.d("UID Clicked:",uid.text.toString())
                listener.onClick(position)

                startActivity(context,
                    Intent(context, perfil_socorrista::class.java).apply {
                        putExtra("nome",nome.text.toString());
                        putExtra("uid",uid.text.toString())
                        putExtra("imagePath",imagePath.text.toString())
                    },
                    null)
            }
        }


    }




     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PerfilViewHolder {
        //Responsavel por criar as vizualizações da lista
        val itemLIsta = LayoutInflater.from(context).inflate(R.layout.perfil_cliente,parent,false)//nao usa o root
        //.from() indica o contexto que esta passndo esse inflate
        val holder = PerfilViewHolder(itemLIsta)
        return holder // criado a vizualiçaõ
    }
        override fun getItemCount(): Int {
            return perfil.size

    }


    override fun onBindViewHolder(holder: PerfilViewHolder, position: Int) {
        // esse metodo cria a vizualição dos view que foram criados
        holder.foto.setImageResource(perfil[position].fot)
        holder.nome.text = perfil[position].nome
        holder.descrição.text=perfil[position].Descrição
        //holder.uid.text=perfil[position].uid
        //holder.imagePath.text=perfil[position].imagePath
    }

    interface  Myclicklistenner{
        fun onClick(position: Int)
    }



}
//inner é uma classe interna
//é necessario criar o construtor da classe RecycleView.ViewHolder -> o iten view
//é necessario criar Metodos --onCreatViewHolder
//                              onBindViewHolder
//                              getItemcout