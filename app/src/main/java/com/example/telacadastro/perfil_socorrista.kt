package com.example.telacadastro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.telacadastro.databinding.ActivityCriarcontaBinding
import com.example.telacadastro.databinding.ActivityPerfilSocorristaBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.HttpsCallableResult
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase

class perfil_socorrista : AppCompatActivity() {

    private lateinit var functions : FirebaseFunctions
    private lateinit var binding: ActivityPerfilSocorristaBinding
    private lateinit var auth: FirebaseAuth

    companion object{
        const val  LETTER = "latter"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        functions = Firebase.functions("southamerica-east1")
        super.onCreate(savedInstanceState)
        val letterId = intent?.extras?.getString("LETTER").toString()
        binding = ActivityPerfilSocorristaBinding.inflate(layoutInflater)
        setContentView(binding.root)





        auth = Firebase.auth
        val user = Firebase.auth.currentUser
        user?.let {

            val uid = it.uid
        }


        //binding.tvNome.setText("adicionar o id do usuarioa aqui")
        val uid = intent?.getStringExtra("emergencyUid").toString();

        val nome = intent?.getStringExtra("nome").toString();

        binding.tvNome.text = nome

        Log.d("INTENT EXTRA",uid)

        binding.BtnAceitar.setOnClickListener {
            Log.d("SOCORRISTA SCREEN","BTN PRESSED")
            if (user != null) {
                acceptEmergency(uid,user.uid)
            }
        }
        binding.BtnRejeitar.setOnClickListener {
            if (user != null) {
                refuseEmergency(uid,user.uid)
            }

        }
    }
    fun acceptEmergency(emergency: String, uid: String): Task<HttpsCallableResult> {
        Log.d("FUNCTION", "START")
        val data = hashMapOf(
            "emergency" to emergency,
            "userName" to uid
        )
        return functions
            .getHttpsCallable("acceptEmergency")
            .call(data)
    }

    fun refuseEmergency(emergency: String, uid: String): Task<HttpsCallableResult> {
        Log.d("FUNCTION", "START")
        val data = hashMapOf(
            "emergency" to emergency,
            "userName" to uid
        )
        return functions
            .getHttpsCallable("refuseEmergency")
            .call(data)
    }
}