package com.example.telacadastro

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.telacadastro.adapter.AdpterPerfil
import com.example.telacadastro.databinding.ActivityRecycleVeiwBinding
import com.example.telacadastro.model.perfil
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase


private lateinit var binding: ActivityRecycleVeiwBinding
private lateinit var functions: FirebaseFunctions
private var perfil: MutableList<perfil> = mutableListOf()
private lateinit var adpterPerfil: AdpterPerfil

class recycleVeiw : AppCompatActivity(),AdpterPerfil.Myclicklistenner {
    override fun onCreate(savedInstanceState: Bundle?) {
        functions = Firebase.functions("southamerica-east1")
        super.onCreate(savedInstanceState)
        binding = ActivityRecycleVeiwBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recyclerView.layoutManager =
            LinearLayoutManager(this)//definindo em qual posicção vai aparecer as views,por padrao já vem na vertical
        binding.recyclerView.setHasFixedSize(true)//como verdadeiro, da mais desenpenho na reciclagem da lista
        // configurando o adapter


        //swipe recyclerview
        val swipe = object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.END){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                perfil.removeAt(viewHolder.adapterPosition)//removendo
                adpterPerfil.notifyDataSetChanged()//notoficando a lista
            }//metodos obrigatorios onSwipe desliza e some da lista
        }//variavel vai receber um objeto,Dirs é onde configura a direção
        val itemTouchHelper=ItemTouchHelper(swipe)
        //anexar o swipe na recycleview
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)

        adpterPerfil = AdpterPerfil(this, perfil,this@recycleVeiw)


        //o construtor espera os parametros  da classe adpeterperfil lista
        binding.recyclerView.adapter = adpterPerfil
        //criando os itens da lista
//        val perfeil1 = perfil(
//            R.drawable.baseline_person_24,
//            "Nome do usuario",
//            "Distância,Tempo de Espera",
//            "Analisar"
//        )
//        // adicionar na lista d chamada(perfil)
//        listadePerfil.add(perfeil1)



        fun callListAllEmergencies() {

            val data = hashMapOf<String, Any>() // Pass any necessary data to the function

            val result = functions.getHttpsCallable("listAllEmergencies")
                .call(data)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val result =
                            task.result?.data.toString()  //[ {uid=2211221121221, nome=guts, status=new}, {uid=2112122121, nome=uwu, status=new}]

                        Log.d(
                            "RESULT:",
                            result.toString()
                        ) //[ {uid=2211221121221, nome=guts, status=new}, {uid=2112122121, nome=uwu, status=new}]

                        val list = result
                            .substring(1, result.length - 1) // Remove the square brackets
                            .split("}, ") // Split the elements

                        val hashMapList = list.map { element ->
                            val keyValuePairs = element
                                .replace("{", "")
                                .replace("}", "")
                                .split(", ")

                            val hashMap = hashMapOf<String, Any>()
                            keyValuePairs.forEach { pair ->
                                val keyValue = pair.split("=")
                                val key = keyValue[0].trim()
                                val value = keyValue[1].trim()

                                hashMap[key] = value

                            }
                            Log.d("NEW HASHMAP:", hashMap.toString())
                            var profile = perfil(
                                R.drawable.baseline_person_24,
                                hashMap["nome"] as String,
                                "Distância,Tempo de Espera",
                                "Analisar"
                            )

                            perfil.add(profile)
                        }
                        binding.recyclerView.adapter = adpterPerfil
                    }
                }
        }
        callListAllEmergencies()


    }


    private  fun  irTelaperfil(){
        var iranalise = Intent(this,perfil_socorrista::class.java)
        startActivity(iranalise)
    }

    override fun onClick(position: Int) {
        when(position){
            0->startActivity(Intent(this,perfil_socorrista::class.java))
        }

    }


}