package com.example.telacadastro

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.telacadastro.databinding.ActivityEmergenciaBinding
import com.google.firebase.firestore.util.Util
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream

private const val REQUEST_IMAGE_CAPTURE = 1
class Emergencia : AppCompatActivity() {

    private lateinit var binding : ActivityEmergenciaBinding
    var storage : FirebaseStorage? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmergenciaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnEnviar.setOnClickListener{btnir ->
            uploadimage()
        }
        binding.btnEnviar
        storage= Firebase.storage
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }
    private fun obterImagemGaleria(){
        val intent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(Intent.createChooser(intent,"escolha uma imagem"),11)
    }
    private fun TirarFoto(){
        val intent=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
    }






    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_submenu2 -> {
                obterImagemGaleria()
                true
            }
            R.id.action_submenu->{
                TirarFoto()
                true
            }
            else ->{
                return super.onOptionsItemSelected(item)
            }
        }

    }
    override fun onActivityResult(requestCode: Int,resultCode: Int,data:Intent?){
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode== REQUEST_IMAGE_CAPTURE && resultCode== RESULT_OK){
            val imageBitMap= data?.extras?.get("data") as Bitmap
            binding.uploadImageView.setImageBitmap(imageBitMap)
    }}




    private fun uploadimage(){
        val bitmap=(binding.uploadImageView.drawable as BitmapDrawable).bitmap
        val bounce = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG,20,bounce)
        val data = bounce.toByteArray()
        val reference = storage!!.reference.child("imagens").child("uploadimage.png")
        var uploadTask = reference.putBytes(data)
        uploadTask.addOnFailureListener {
            Toast.makeText(baseContext,"Erro",Toast.LENGTH_SHORT).show()
            // Handle unsuccessful uploads
        }.addOnSuccessListener { taskSnapshot ->
            Toast.makeText(baseContext,"Sucesso",Toast.LENGTH_SHORT).show()
            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
            // ...
        }

    }


}