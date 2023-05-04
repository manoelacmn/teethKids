package com.example.telacadastro

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.telacadastro.databinding.TelaLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class telaLogin:AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: TelaLoginBinding


    override fun onCreate(savedInstanceState : Bundle?){
        super.onCreate(savedInstanceState)
        binding = TelaLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        binding.cadastro.setOnClickListener{tvcadastro-> ircadastro()}

        binding.BtnEntar.setOnClickListener {btn ->

            if(binding.EtSenha.text.isNullOrEmpty()){
                Toast.makeText(baseContext,"ze da manga",Toast.LENGTH_LONG).show()


            }else if (binding.EtUsuario.text.isNullOrEmpty()){
                Toast.makeText(baseContext,"ze da manga",Toast.LENGTH_LONG).show()
            }else{
                val password = binding.EtSenha.text.toString();
                val email = binding.EtUsuario.text.toString();
                signIn(email,password);
            }




    }
    }




    private fun signIn(email: String, password: String) {
        // [START sign_in_with_email]
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "logged as ,${email}",
                        Toast.LENGTH_SHORT,
                    ).show()
                    irTelaInicial()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
        // [END sign_in_with_email]
    }



    private fun ircadastro(){
        var telacadastro = Intent(this,criarConta::class.java)
        startActivity(telacadastro)
    }


    private fun irTelaInicial(){
        var telaI = Intent(this,Tela_Inicial::class.java)
        startActivity(telaI)
    }
    companion object {
        private const val TAG = "EmailPassword"
    }

}