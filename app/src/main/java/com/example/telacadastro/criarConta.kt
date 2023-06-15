package com.example.telacadastro

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.telacadastro.databinding.ActivityCriarcontaBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.json.JSONObject
import java.io.BufferedReader
import java.net.HttpURLConnection
import java.net.URL



class criarConta: AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
// ...
// Initialize Firebase Auth



    private lateinit var binding: ActivityCriarcontaBinding

    private fun hideSoftKeyboard(){
        val softKeyManager =
            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        softKeyManager.hideSoftInputFromWindow(binding.BtnCriarConta.windowToken, 0)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        auth = Firebase.auth
        super.onCreate(savedInstanceState)
        binding = ActivityCriarcontaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.BtnCriarConta.setOnClickListener { btnCriarConta ->
            hideSoftKeyboard()
            val email = binding.EtEmailCriarConta.text.toString()
            val senha = binding.EtSenhaCriarConta.text.toString()
            val nome = binding.EtNomeCriarConta.text.toString()
            val endereco = binding.EtEnderecoCriarConta.text.toString()
            val cv = binding.EtCurriculoCriarConta.text.toString()

            singUpNewAccount(nome , email, senha,cv ,endereco)
            registerForContextMenu(binding.EtEnderecoCriarConta)

            if(binding.EtEnderecoCriarConta.text.toString().length == 8){
            }else{Toast.makeText(this,"CEP invalido",Toast.LENGTH_SHORT).show()}

        }
    }


    private fun singUpNewAccount(nome: String , email: String, password: String, cv: String,endereco: String )
    {
        val db = Firebase.firestore
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    Toast.makeText(
                        baseContext,
                        "Authentication succeeded",
                        Toast.LENGTH_SHORT,
                    ).show()

                    val usuario = hashMapOf(
                        "email" to email,
                        "senha" to password,
                        "nome" to nome,
                        "endereco" to endereco,
                        "curriculo" to cv,
                        "uid" to user?.uid
                    )
                    db.collection("usuarios").add(usuario)

                    val avaliacoesCollection = db.collection("avaliacoes")

                    val data1 = hashMapOf(
                        "uid" to user?.uid,
                    )
                    avaliacoesCollection.add(data1)
                        .addOnSuccessListener {
                            // Document added successfully
                            println("Avaliacao added successfully")
                        }
                        .addOnFailureListener { exception ->
                            // Error occurred while adding document
                            println("Failed to add avaliacao: $exception")
                        }


                    irParaTelaLogin()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                    //updateUI(null)
                }
            }
    }


    private fun irParaTelaLogin(){
        val telaLog = Intent(this,telaLogin::class.java)
        startActivity(telaLog)
    }
    companion object {
        private const val TAG = "singUp"

    }

}

