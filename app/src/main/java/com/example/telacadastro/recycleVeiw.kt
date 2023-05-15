package com.example.telacadastro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.telacadastro.adapter.adpterPerfil
import com.example.telacadastro.databinding.ActivityRecycleVeiwBinding
import com.example.telacadastro.model.perfil
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await


private lateinit var binding: ActivityRecycleVeiwBinding
private lateinit var functions: FirebaseFunctions

class recycleVeiw : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        functions = Firebase.functions("southamerica-east1")
        super.onCreate(savedInstanceState)
        binding = ActivityRecycleVeiwBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recyclerView.layoutManager =
            LinearLayoutManager(this)//definindo em qual posicção vai aparecer as views,por padrao já vem na vertical
        binding.recyclerView.setHasFixedSize(true)//como verdadeiro, da mais desenpenho na reciclagem da lista
        // configurando o adapter





        val listadePerfil: MutableList<perfil> = mutableListOf()
        val adapterPerfil = adpterPerfil(
            this,
            listadePerfil
        )//o construtor espera os parametros  da classe adpeterperfil lista
        binding.recyclerView.adapter = adapterPerfil
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

                            listadePerfil.add(profile)
                        }
                        binding.recyclerView.adapter = adapterPerfil
                    }
                }
        }
        callListAllEmergencies()
    }
}