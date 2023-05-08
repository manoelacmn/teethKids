package com.example.telacadastro

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.telacadastro.databinding.ActivityEmergenciaBinding
import java.io.ByteArrayOutputStream

class Emergencia : AppCompatActivity() {

    private lateinit var binding : ActivityEmergenciaBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmergenciaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnEnviar.setOnClickListener{btnir ->

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

    }
}