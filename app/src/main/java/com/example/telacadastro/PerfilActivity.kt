package com.example.telacadastro

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.telacadastro.databinding.ActivityPerfilBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

private lateinit var binding: ActivityPerfilBinding
class PerfilActivity : AppCompatActivity() {
    val db = Firebase.firestore


    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.BtnEditar.setOnClickListener { btn-> reputacao() }
        binding.BtnReputacao.setOnClickListener { btn->editar() }
        binding.BTNTirarfoto.setOnClickListener { btn->tirarfoto() }

        val db = Firebase.firestore

        auth = Firebase.auth
        val user = Firebase.auth.currentUser
        user?.let {
            val uid = it.uid
        }

        Log.d("userUID",user!!.uid)
        db.collection("usuarios").whereEqualTo("uid",user!!.uid)
            .get()
            .addOnSuccessListener { document ->
                for (document in document) {
                    Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                    binding.tvnome.text = document.data["nome"].toString()
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }

        Log.d("userUID",user!!.uid)
        db.collection("usuarios").whereEqualTo("uid",user!!.uid)
            .get()
            .addOnSuccessListener { document ->
                for (document in document) {
                    Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                    binding.tvendereco1.text= document.data["endereços"].toString()
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
        Log.d("userUID",user!!.uid)
        db.collection("usuarios").whereEqualTo("uid",user!!.uid)
            .get()
            .addOnSuccessListener { document ->
                for (document in document) {
                    Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                    binding.tvemail.text = document.data["email"].toString()
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }

   private fun tirarfoto(){
       val intent=Intent(this,tirarfot::class.java)
       startActivity(intent)
   }

    private fun reputacao(){
        val intent = Intent(this,MainActivity2::class.java)
        startActivity(intent)
    }
    private fun editar(){
        val intent = Intent(this,recycleravaliacao::class.java)
        startActivity(intent)
    }


}