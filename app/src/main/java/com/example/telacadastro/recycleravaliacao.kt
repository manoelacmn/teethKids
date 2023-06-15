package com.example.telacadastro

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.telacadastro.adapter.AdapterAvaliacao
import com.example.telacadastro.databinding.ActivityRecycleravaliacaoBinding
import com.example.telacadastro.model.avaliacao
import com.google.android.gms.tasks.Task
import com.google.common.reflect.TypeToken
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.HttpsCallableResult
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson

class recycleravaliacao : AppCompatActivity() {


    private lateinit var binding: ActivityRecycleravaliacaoBinding
    private  var listaavaliacao:MutableList<avaliacao> = mutableListOf()
    private lateinit var AdpterAvaliacao:AdapterAvaliacao
    private lateinit var functions: FirebaseFunctions
    private lateinit var auth: FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityRecycleravaliacaoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = Firebase.firestore


        functions = Firebase.functions("southamerica-east1")
        binding.recycleAvaliacao.layoutManager=
            LinearLayoutManager(this)
        binding.recycleAvaliacao.setHasFixedSize(true)

        AdpterAvaliacao= AdapterAvaliacao(this,listaavaliacao)
        binding.recycleAvaliacao.adapter = AdpterAvaliacao

        auth = Firebase.auth
        val user = Firebase.auth.currentUser
        user?.let {
            val uid = it.uid
        }


        db.collection("avaliacoes")
            .whereEqualTo("uid", user!!.uid)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d("DOCUMENTS", "${document.id} => ${document.data["avaliacoes"]}")

                    val avalicaos = document.data["avaliacoes"].toString()


                    val list = avalicaos
                        .substring(1, avalicaos.length - 1) // Remove the square brackets
                        .split("}, ") // Split the elements

                    val hashMapList = list.map { element ->
                        val keyValuePairs = element
                            .replace("{", "")
                            .replace("}", "")
                            .split(", ")

                        val hashMap = hashMapOf<String, Any>()
                        Log.d("CREATING RESULT", list.toString())
                        keyValuePairs.forEach { pair ->
                            val keyValue = pair.split("=")
                            val key = keyValue[0].trim()
                            val value = keyValue[1].trim()
                            hashMap[key] = value
                            Log.d("CREATING HASHMAP", hashMap.toString())

                        }
                        val newItem = avaliacao(
                            hashMap["nome"] as String,
                            hashMap["rating"].toString().toDouble(),
                            hashMap["text"].toString()
                        )
                        Log.d("NEW ITEM",newItem.toString())
                        listaavaliacao.add(newItem)

                        AdpterAvaliacao= AdapterAvaliacao(this,listaavaliacao)
                        binding.recycleAvaliacao.adapter = AdpterAvaliacao

                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents: ", exception)
            }
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