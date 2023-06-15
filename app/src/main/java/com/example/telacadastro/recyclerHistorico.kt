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
import com.google.gson.Gson
import org.json.JSONArray
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class recyclerHistorico : AppCompatActivity() {

    private var listaHistorico: MutableList<Historico> = mutableListOf()
    private lateinit var AdpterHistorico: AdapterHistorico
    private lateinit var binding: ActivityRecyclerHistoricoBinding
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

        db.collection("historico")
            .whereEqualTo("uid", user!!.uid)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                    Log.d("HISTORIC OBJECT: ", document.data["historic"].toString())
                }
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents: ", exception)
            }

        fun callAllAvaliations() {
            val data = hashMapOf<String, Any>(
                "uid" to user!!.uid
            )

            val result = functions.getHttpsCallable("getHistoric")
                .call(data)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val result = task.result?.data.toString()
                        Log.d("RESULT:", result.toString())

                        val jsonArray = JSONArray(result)

                        for (i in 0 until jsonArray.length()) {
                            val jsonObjectArray = jsonArray.getJSONArray(i)

                            for (j in 0 until jsonObjectArray.length()) {
                                val jsonObject = jsonObjectArray.getJSONObject(j)

                                val concludedTime = jsonObject.optJSONObject("concludedTime")
                                val uid = jsonObject.optString("uid")
                                val isRated = jsonObject.optBoolean("isRated")
                                val emergencyRef = jsonObject.optString("emergencyRef")



                                val concludedTimeObject = jsonObject.optJSONObject("concludedTime")
                                val seconds = concludedTimeObject?.optLong("_seconds")
                                val nanoseconds = concludedTimeObject?.optInt("_nanoseconds")

                                val timestamp = seconds?.let { nanoseconds?.let { it1 -> Instant.ofEpochSecond(it, it1.toLong()) } }
                                val dateTime = LocalDateTime.ofInstant(timestamp, ZoneId.systemDefault())
                                val formattedDateTime = dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))




                                Log.d("JSON Object $i:$j", "concludedTime: $formattedDateTime")
                                Log.d("JSON Object $i:$j", "uid: $uid")
                                Log.d("JSON Object $i:$j", "isRated: $isRated")
                                Log.d("JSON Object $i:$j", "emergencyRef: $emergencyRef")
                            }
                        }
                    }
                }
        }

        callAllAvaliations()

        binding.recycleHistorico.layoutManager = LinearLayoutManager(this)
        binding.recycleHistorico.setHasFixedSize(true)
        AdpterHistorico = AdapterHistorico(this, listaHistorico)
        binding.recycleHistorico.adapter = AdpterHistorico

        itens()
    }

    private fun itens() {
        val nome = Historico("pereira", "quebrou o dente fazendo capoeira", "10/05/2001-22:53")
        listaHistorico.add(nome)
    }
}
