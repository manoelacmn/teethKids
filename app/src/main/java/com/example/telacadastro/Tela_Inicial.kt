package com.example.telacadastro

import android.content.ContentValues
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.telacadastro.databinding.ActivityTelaInicialBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.google.gson.Gson

data class Data(
    val senha: String?,
    val uid: String?,
    val current: Current?,
    val telefone: String?,
    val endereco: String?,
    val curriculo:String?,
    val nome: String?,
    val email: String?,
    val fcmtoken: String?,
    val enderecos: List<String>?,
    val status: String?,
    val profilePhoto: String?
)

data class Current(
    val emergencyPATH: String?
)

private lateinit var binding: ActivityTelaInicialBinding
lateinit var storage: FirebaseStorage


class Tela_Inicial : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        storage = Firebase.storage
        val storageReference = FirebaseStorage.getInstance().reference
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

                    // document.data =  {senha=asriel, uid=jhyjNs3cG7O0W9XYbXlloZHR1ot2, current={emergencyPATH=femboyFurry}, telefone=seeszeee, endereco=asriel, curriculo=, nome=asriel, email=asriel@dreemurr.com, fcmtoken=cCBb6Vt4QAmlfGDxDJH7wS:APA91bE9EstYRHzLjhiXkODdS_lrIuYsGmeG_HlvRXPSb9AK8U_oFxi4TDX9qX-n5Qxd1fzSlAFsqbb0wLHDQjmnv2n23YTgTY_mbKEwQDZAxPT71oyyJZ1LdkyUF8AYXvmgW6T2yWMq, endereços=[I m tired terdd, tf foi fqddfwfwd, rfrfdfffghh], status=busy}
                    val dataString = gson.toJson(document.data)
                    val jsonData: Data = gson.fromJson(dataString, Data::class.java)

                    Log.d("DATASTRING",dataString)

                    Log.d("PROFILE PHOTO",jsonData.profilePhoto.toString())


                    val imageRef1 = jsonData.profilePhoto?.toString()
                        ?.let { storageReference.child(it) }

                    val multiGiga: Long = 4096 * 4096
                    imageRef1?.getBytes(multiGiga)?.addOnSuccessListener { image->
                        val profilePhoto = BitmapFactory.decodeByteArray(image, 0, image.size)
                        binding.imageView.setImageBitmap(profilePhoto)
                    }?.addOnFailureListener {
                        // Handle any errors
                    }




                    binding.tvTeethKids.text = jsonData.nome ?: ""




                    val currentEmergencyPath = gson.toJson(document.data["current"])


                    Log.d("DATASTRING",currentEmergencyPath.toString())



                    Log.d("Debug1", "Current Emergency Path: $currentEmergencyPath")


//                    binding.btnLog.setOnClickListener { btn ->
//                        atual("", currentEmergencyPath)
//                    }
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
        binding.btnLog.setOnClickListener { btn->ir() }

    }

    private fun irTelaperfil() {
        val irPerfil = Intent(this, PerfilActivity::class.java)
        startActivity(irPerfil)
    }
    private fun ir(){
        val intent = Intent(this,segundaConfirmacao::class.java)
        startActivity(intent)
    }

    private fun emergencia() {
        val irEmergencia = Intent(this, recycleVeiw::class.java)
        startActivity(irEmergencia)
    }

    private fun configuracao() {
        val intent = Intent(this, MainActivity2::class.java)
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

    private fun atual(addresses: List<String>?, currentEmergencyPath: String?) {
        val intent = Intent(this, segundaConfirmacao::class.java)
        intent.putExtra("addresses", addresses?.toTypedArray())
        intent.putExtra("currentEmergencyPath", currentEmergencyPath)
        Log.d("Debug", "Addresses: ${addresses?.joinToString()}")
        Log.d("Debug", "Current Emergency Path: $currentEmergencyPath")
        startActivity(intent)
    }
}
