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
import com.google.gson.Gson

data class Data(
    val senha: String?,
    val uid: String?,
    val current: Current?,
    val telefone: String?,
    val endereco: String?,
    val curriculo: String?,
    val nome: String?,
    val email: String?,
    val fcmtoken: String?,
    val enderecos: List<String>?,
    val status: String?
)

data class Current(
    val emergencyPATH: String?
)

private lateinit var binding: ActivityTelaInicialBinding

class Tela_Inicial : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        val db = Firebase.firestore

        auth = Firebase.auth
        val user = Firebase.auth.currentUser
        user?.let {
            val uid = it.uid
        }
        super.onCreate(savedInstanceState)
        binding = ActivityTelaInicialBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d("userUID", user!!.uid)

        db.collection("usuarios")
            .whereEqualTo("uid", user!!.uid)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                    // document.data =  senha=asriel, uid=jhyjNs3cG7O0W9XYbXlloZHR1ot2, current={emergencyPATH=femboyFurry}, telefone=seeszeee, endereco=asriel, curriculo=, nome=asriel, email=asriel@dreemurr.com, fcmtoken=cCBb6Vt4QAmlfGDxDJH7wS:APA91bE9EstYRHzLjhiXkODdS_lrIuYsGmeG_HlvRXPSb9AK8U_oFxi4TDX9qX-n5Qxd1fzSlAFsqbb0wLHDQjmnv2n23YTgTY_mbKEwQDZAxPT71oyyJZ1LdkyUF8AYXvmgW6T2yWMq, endereços=[I m tired terdd, tf foi fqddfwfwd, rfrfdfffghh], status=busy}
                    val dataString = gson.toJson(document.data)
                    val jsonData: Data = gson.fromJson(dataString, Data::class.java)
                    binding.tvTeethKids.text = jsonData.nome ?: ""

                    val addresses = jsonData.enderecos
                    if (addresses != null) {
                        for (address in addresses) {
                            // Process each address here
                            Log.d("Address", address)
                        }
                    }

                    val current = jsonData.current
                    val emergencyPath = current?.emergencyPATH
                    if (emergencyPath != null) {
                        // Process the emergency path here
                        Log.d("EmergencyPath", emergencyPath)
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents: ", exception)
            }

        binding.btnTelaPerfil.setOnClickListener { btnir ->
            irTelaperfil()
        }
        binding.btnTelaEmergencia.setOnClickListener { btnir ->
            emergencia()
        }
        binding.btnConfiguraO.setOnClickListener({ btn -> configuracao() })
        binding.btnAvaliaO.setOnClickListener { btn -> avaliacao() }
        binding.btnHistorico.setOnClickListener { btn -> historico() }
        binding.btnLog.setOnClickListener { btn -> atual() }
    }

    private fun irTelaperfil() {
        val irPerfil = Intent(this, PerfilActivity::class.java)
        startActivity(irPerfil)
    }

    private fun emergencia() {
        val irEmergencia = Intent(this, recycleVeiw::class.java)
        startActivity(irEmergencia)
    }

    private fun configuracao() {
        val intent = Intent(this, Emergencia::class.java)
        startActivity(intent)
    }

    private fun avaliacao() {
        val intent = Intent(this, recycleravaliacao::class.java)
        startActivity(intent)
    }

    private fun historico() {
        val intent = Intent(this, recyclerHistorico::class.java)
        startActivity(intent)
    }

    private fun atual() {
        val intent = Intent(this, segundaConfirmacao::class.java)
        startActivity(intent)
    }
}
