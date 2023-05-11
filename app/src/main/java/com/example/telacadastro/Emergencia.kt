package com.example.telacadastro

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.telacadastro.databinding.ActivityEmergenciaBinding
import com.google.firebase.firestore.util.Util
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
            uploadimage()
        }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode==Activity.RESULT_OK){
            if(requestCode == 11){
                if(data != null){
                    val uri = data.data
                    binding.uploadImageView.setImageURI(uri)
                }
            }
        }
    }





    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_submenu2 -> {
                obterImagemGaleria()
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
            Toast.makeText(baseContext,"Erro",Toast.LENGTH_SHORT).show()
            // Handle unsuccessful uploads
        }.addOnSuccessListener { taskSnapshot ->
            Toast.makeText(baseContext,"Sucesso",Toast.LENGTH_SHORT).show()
            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
            // ...
        }

    }


}