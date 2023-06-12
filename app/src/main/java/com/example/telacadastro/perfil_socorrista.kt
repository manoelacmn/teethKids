package com.example.telacadastro

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.graphics.drawable.toIcon
import com.example.telacadastro.databinding.ActivityCriarcontaBinding
import com.example.telacadastro.databinding.ActivityPerfilSocorristaBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.HttpsCallableResult
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

class perfil_socorrista : AppCompatActivity() {

    private lateinit var functions : FirebaseFunctions
    private lateinit var binding: ActivityPerfilSocorristaBinding
    private lateinit var auth: FirebaseAuth
    lateinit var storage: FirebaseStorage


    companion object{
        const val  LETTER = "latter"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        storage = Firebase.storage
        val storageReference = FirebaseStorage.getInstance().reference


        var uid:String = ""
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
//        val uid = intent?.getStringExtra("emergencyUid").toString();

        val nome = intent?.getStringExtra("nome").toString();

        uid = intent?.getStringExtra("uid").toString();

        val imagePath2 = intent?.getStringExtra("imagePath2").toString();

        val imagePath1 = intent?.getStringExtra("imagePath1").toString();

        val  imagePath = intent?.getStringExtra("imagePath").toString();

        binding.tvNome.text = nome

        Log.d("IMGEPATH",imagePath)
        Log.d("INTENT EXTRA",uid)


        val imageRef = storageReference.child(imagePath)

//        binding.imageView5

        val ONE_MEGABYTE: Long = 4096 * 4096
        imageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener {image->
            val uwu = BitmapFactory.decodeByteArray(image, 0, image.size)
            binding.imageView5.setImageBitmap(uwu)
        }.addOnFailureListener {
            // Handle any errors
        }


        binding.BtnAceitar.setOnClickListener {
            Log.d("SOCORRISTA SCREEN","BTN PRESSED")
            Log.d("EMERGENCY UID:",uid)
            if (user != null) {
                Log.d("USER UID",user.uid)
            }
            if (user != null) {
                acceptEmergency(uid,user.uid)
            }
        }
        binding.BtnRejeitar.setOnClickListener {
            Log.d("EMERGENCY UID:",uid)
            if (user != null) {
                Log.d("USER UID",user.uid)
            }
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