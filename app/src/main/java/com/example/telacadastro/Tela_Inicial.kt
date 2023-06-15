package com.example.telacadastro

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.telacadastro.databinding.ActivityTelaInicialBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import io.grpc.Internal


private lateinit var binding: ActivityTelaInicialBinding
private lateinit var functions: FirebaseFunctions


class Tela_Inicial : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        val db = Firebase.firestore

        auth = Firebase.auth
        val user = Firebase.auth.currentUser
        user?.let {
            val uid = it.uid
        }
        super.onCreate(savedInstanceState)
        binding= ActivityTelaInicialBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var hasActiveEmergency: Boolean = false

        Log.d("userUID",user!!.uid)


        db.collection("usuarios")
            .whereEqualTo("uid", user!!.uid)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                    if(document.data["current"]!= null) run {
                        hasActiveEmergency = true
                        Log.d("HAS ACTIVE EMERGERNCY", hasActiveEmergency.toString())


                    }
                    binding.tvTeethKids.text = document.data["nome"].toString()
                    binding.tvTeethKids.text = document.data[""].toString()
                }
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents: ", exception)
            }



        functions = Firebase.functions("southamerica-east1")


        fun isBusy(uid: String): Task<String> {
            // Create the arguments to the callable function.
            val data = hashMapOf(
                "userUid" to uid,
            )

            return functions
                .getHttpsCallable("isBusy")
                .call(data)
                .continueWith { task ->
                    // This continuation runs on either success or failure, but if the task
                    // has failed then result will throw an Exception which will be
                    // propagated down.
                    val result = task.result?.data as String
                    Log.d("RESULT:",result)
                    result
                }


        }

        isBusy(user.uid)





        binding.btnTelaPerfil.setOnClickListener{btnir ->
            irTelaperfil()
        }
        binding.btnTelaEmergencia.setOnClickListener { btnir ->

            if(hasActiveEmergency){
                val goToEmergencia = Intent(this,current_emergency::class.java)
                startActivity(goToEmergencia)
            }else{
                emergencia()
            }
        }
        binding.btnConfiguraO.setOnClickListener({btn -> tirarFoto()})
        binding.btnAvaliaO.setOnClickListener{btn->avaliacao()}




    }

    private fun irTelaperfil(){
        var irPerfil = Intent(this,MainActivity2::class.java)
        startActivity(irPerfil)
    }
    private fun emergencia(){
        var irEmergencia = Intent(this,recycleVeiw::class.java)
        startActivity(irEmergencia)
    }
    private fun tirarFoto(){
        var intent=Intent(this,Emergencia::class.java)
        startActivity(intent)
    }
    private fun avaliacao(){
        var intent=Intent(this,recycleravaliacao::class.java)
        startActivity(intent)
    }
}