package com.example.telacadastro

import AdapterHistorico
import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.telacadastro.adapter.AdapterAvaliacao
import com.example.telacadastro.databinding.ActivityRecyclerHistoricoBinding
import com.example.telacadastro.model.Historico
import com.example.telacadastro.model.avaliacao
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import org.json.JSONArray

class recyclerHistorico : AppCompatActivity() {

    private var listaHistorico: MutableList<Historico> = mutableListOf()
    private lateinit var AdpterHistorico: AdapterHistorico
    private lateinit var binding:ActivityRecyclerHistoricoBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var functions: FirebaseFunctions


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecyclerHistoricoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val db = Firebase.firestore
        functions = Firebase.functions("southamerica-east1")


        auth = Firebase.auth
        val user = Firebase.auth.currentUser
        user?.let {
            val uid = it.uid
        }


        db.collection("historico")
            .whereEqualTo("uid", user!!.uid)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                    Log.d("HISTOIC OBJECT: ",document.data["historic"].toString())
//                    val jsonArray = JSONArray(document.data.toString())
//                    Log.d("NEW ARRAY",jsonArray.toString())

//                    for (i in 0 until jsonArray.length()){
//                        Log.d("NEW ARRAY",i.toString())
//                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents: ", exception)
            }

        fun callAllAvaliations() {

            val data = hashMapOf<String, Any>(
                "uid" to user!!.uid
            ) // Pass any necessary data to the function


            val result = functions.getHttpsCallable("getHistoric")
                .call(data)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val result =
                            task.result?.data.toString()  //[ {uid=2211221121221, nome=guts, status=new}, {uid=2112122121, nome=uwu, status=new}]

                        Log.d(
                            "RESULT:",
                            result.toString()
                        )


                        val jsonArray = JSONArray(result)

                        Log.d("JSON ARRAY",jsonArray.toString())

                        for (i in 0 until jsonArray.length()) {
                            val jsonObject = jsonArray.getJSONObject(i)

                            val concludedTime = jsonObject.opt("concludedTime")
                            val uid = jsonObject.opt("uid")
                            val isRated = jsonObject.opt("isRated")
                            val emergencyRef = jsonObject.opt("emergencyRef")

                            println("concludedTime: $concludedTime")
                            println("uid: $uid")
                            println("isRated: $isRated")
                            println("emergencyRef: $emergencyRef")

                        }


//                        val list = result
//                            .substring(1, result.length - 1) // Remove the square brackets
//                            .split("}, ") // Split the elements

//                        val hashMapList = list.map { element ->
//                            val keyValuePairs = element
//                                .replace("{", "")
//                                .replace("}", "")
//                                .split(", ")
//
                        val hashMap = hashMapOf<String, Any>()
//                            Log.d("CREATING RESULT",list.toString())
//                            keyValuePairs.forEach { pair ->
//                                val keyValue = pair.split("=")
//                                val key = keyValue[0].trim()
//                                val value = keyValue[1].trim()
//                                hashMap[key] = value
//                                Log.d("CREATING HASHMAP",hashMap.toString())
//                            }
                        Log.d("NEW HASHMAP:", hashMap.toString())

                    }
                }
        }
        callAllAvaliations()




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