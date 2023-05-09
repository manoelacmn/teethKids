package com.example.telacadastro

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.telacadastro.databinding.ActivityEmergenciaBinding
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream

class Emergencia : AppCompatActivity() {

    private lateinit var binding : ActivityEmergenciaBinding
    var storage : FirebaseStorage? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmergenciaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnEnviar.setOnClickListener{btnir ->
        }
        storage= Firebase.storage
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_man -> {
                println("main menu clicado")
                true
            }
            else ->{
                return super.onOptionsItemSelected(item)
            }
        }

    }



    private fun iremergencia(){
        var irParaEmerg = Intent(this,Emergencia::class.java)
        startActivity(irParaEmerg)
    }

    private fun uploadimage(){
        val bitmap=(binding.uploadImageView.drawable as BitmapDrawable).bitmap
        val bounce = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG,20,bounce)
        val data = bounce.toByteArray()
        val reference = storage!!.reference.child("imagens").child("uploadimage.png")
        var uploadTask = reference.putBytes(data)
        uploadTask.addOnFailureListener {
            // Handle unsuccessful uploads
        }.addOnSuccessListener { taskSnapshot ->
            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
            // ...
        }

    }


}