package com.example.telacadastro

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.FileProvider
import com.example.telacadastro.databinding.ActivityEmergenciaBinding
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream
import java.io.File

private const val REQUEST_IMAGE_CAPTURE = 1
class tirarfot : AppCompatActivity() {

    private lateinit var binding : ActivityEmergenciaBinding
    var storage : FirebaseStorage? = null
    var uri_Image:Uri?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmergenciaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnEnviar.setOnClickListener{btnir ->
            uploadimage()
        }
        binding.btnEnviar
        storage= Firebase.storage

        var file = Uri.fromFile(File(""))





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
        val autorizacao = "androidx.core.content.FileProvider"
        val diretorio = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val nomeImage = diretorio.path + "/imagemperfil"+ System.currentTimeMillis()+".jpg" //nome do caminho onde vai ser armazenadas
        val file = File(nomeImage)
        uri_Image=FileProvider.getUriForFile(baseContext,autorizacao,file)
        intent.putExtra(MediaStore.EXTRA_OUTPUT,uri_Image)
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)//comunicacao com a camera, aguardando a resposta =imagem
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
        val reference = storage!!.reference.child("imagens/recivers")
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