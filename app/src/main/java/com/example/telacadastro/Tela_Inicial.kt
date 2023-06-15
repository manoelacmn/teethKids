package com.example.telacadastro

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.telacadastro.databinding.ActivityTelaInicialBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import io.grpc.Internal


private lateinit var binding: ActivityTelaInicialBinding

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


        Log.d("userUID",user!!.uid)


        db.collection("usuarios")
            .whereEqualTo("uid", user!!.uid)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                    binding.tvTeethKids.text = document.data["nome"].toString()
                }
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents: ", exception)
            }



        binding.btnTelaPerfil.setOnClickListener{btnir ->
            irTelaperfil()
        }
        binding.btnTelaEmergencia.setOnClickListener { btnir ->
            emergencia()
        }
        binding.btnConfiguraO.setOnClickListener({btn -> tirarFoto()})
        binding.btnAvaliaO.setOnClickListener{btn->avaliacao()}
        binding.btnHistorico.setOnClickListener { btn->historico() }
        binding.TvLogout.setOnClickListener{btn->atual()}




    }

    private fun irTelaperfil(){
        var irPerfil = Intent(this,PerfilActivity::class.java)
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
    private fun historico(){
        var intent=Intent(this,recyclerHistorico::class.java)
        startActivity(intent)
    }
    private fun atual(){
        val intent = Intent(this,segundaConfirmacao::class.java)
        startActivity(intent)
    }
}