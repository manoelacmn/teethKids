package com.example.telacadastro

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.util.Log
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.telacadastro.databinding.ActivityMain2Binding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


//val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings");



class MainActivity2 : AppCompatActivity() {

    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings");



    val EXAMPLE_COUNTER = booleanPreferencesKey("isAvalible");
//
//
    suspend fun incrementCounter() {
        dataStore.edit { settings ->
            val currentCounterValue = settings[EXAMPLE_COUNTER] ?: false
            settings[EXAMPLE_COUNTER] = true
        }
    }

//    val exampleCounterFlow  = dataStore.data
//        .map { preferences ->
//            // No type safety.
//            preferences[EXAMPLE_COUNTER] ?: 0
//        }

    private lateinit var auth: FirebaseAuth
    private lateinit var binding:ActivityMain2Binding
    private lateinit var functions: FirebaseFunctions


    @SuppressLint("StringFormatInvalid")
    override fun onCreate(savedInstanceState: Bundle?) {


        functions = Firebase.functions("southamerica-east1")


        val user = Firebase.auth.currentUser
        user?.let {
            // Name, email address, and profile photo Url
            val name = it.displayName
            val email = it.email
            val photoUrl = it.photoUrl

            // Check if user's email is verified
            val emailVerified = it.isEmailVerified

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            val uid = it.uid
        }
        if (user != null) {
//            Toast.makeText(
//                baseContext,
//                user.uid,
//                Toast.LENGTH_SHORT,
//            ).show()

        }


        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            val msg = token
            Log.d(TAG, msg)
//            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()

            val user = Firebase.auth.currentUser
            user?.let {
                // Name, email address, and profile photo Url
                val email = it.email

            }


            fun  updateFcmTokem(email: String,token: String): Task<String> {
                val data = hashMapOf(
                    "fcmtoken" to token,
                    "email" to email
                )
                return functions.getHttpsCallable("updateUserFcm")
                    .call(data)
                    .continueWith {task -> val result = task.result?.data as String
                    result}
            }
            updateFcmTokem(user?.email.toString(),token)
        })



        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)


        var result: String = "offline"
        binding.switch1.setOnCheckedChangeListener{buttonView,isCheked -> isCheked
            if (isCheked) {
                result = "online"
                Log.d("SWITCH", "ACTIVATED")
                Toast.makeText(
                    baseContext,
                    result.toString(),
                    Toast.LENGTH_SHORT,
                ).show()
            }else {
                result ="offline"
                Toast.makeText(
                    baseContext,
                    result,
                    Toast.LENGTH_SHORT,
                ).show()
            }
            binding.switch1.text = result
        }


        binding.btnSalvarDados.setOnClickListener { btnSalvarDados ->
            hideSoftKeyboard()
            val phoneNumber = binding.etTelefone.text.toString()
            val email = binding.etEmailEditarPerfil.text.toString()
            val nome = binding.etNomeEditarPerfil.text.toString()
            val endereco1 = binding.etEndereco1EditarPerfil.text.toString()
            val endereco2 = binding.etEndereco2EditarPerfil.text.toString()
            val endereco3 = binding.etEndereco3EditarPerfil.text.toString()
            val cv = binding.etCurriculoEditarPerfil.text.toString()
            val user = Firebase.auth.currentUser
            user?.let {
                // Name, email address, and profile photo Url
                val uid = it.email

            }

            //singUpNewAccount(nome , email)
            val db = Firebase.firestore



            Log.d("SWITCH STATUS", result)


            val usuario = hashMapOf(
                "userUid" to (user?.uid),
                "phoneNumber" to phoneNumber,
                "nome" to nome,
                "address1" to endereco1,
                "address2" to endereco2,
                "address3" to endereco3,
                "curriculum" to cv,
                "status" to result
            )
            updateUserInfo(usuario);
            db.collection("usuarios").add(usuario)
            Toast.makeText(baseContext,"Atualizado",Toast.LENGTH_LONG).show()
        }




       // binding.switch1.setOnCheckedChangeListener{buttonView,isCheked -> isCheked
       // var ligadoOuDesligado = ""


      //  if (isCheked) {
        //    ligadoOuDesligado = "Ligado"
//            runBlocking { launch {
//                dataStore.edit { settings ->
//                    settings[EXAMPLE_COUNTER] = true
//            } }


//            val status = binding.switch1.text.toString()
//            statustperfil(status)
//
//            runBlocking {
//                launch {
//                    dataStore.edit { settings ->
//                        settings[EXAMPLE_COUNTER] = true
////                        Log.d("CHANGING STATUS:",settings[EXAMPLE_COUNTER].toString())
////                        return@edit{ settings[EXAMPLE_COUNTER]}
//                    }
//
//
////                    val exampleCounterFlow: Flow<Boolean> = dataStore.data.map { preferences ->
////                        preferences[EXAMPLE_COUNTER] ?: 0
////
////                    } as Flow<Boolean>
//                    val boolKey = booleanPreferencesKey("isAvalible")
//
//                    fun isAvalible(): Flow<Boolean> {
//                        return dataStore.data.map {pref->
//                            val availability = pref[boolKey] ?: false
//                            availability
//                        }
//
//                     };
//
//                    val result = isAvalible()
//
//                    Log.d("BOOLKEY",result.toString())
//
//
//                }
//            }

//            Toast.makeText(
//                baseContext,
//                "Online",
//                Toast.LENGTH_SHORT,
//            ).show()
//        }else {
//            ligadoOuDesligado="Desligado"
//            Toast.makeText(
//                baseContext,
//                "Offline",
//                Toast.LENGTH_SHORT,
//            ).show()

//            runBlocking {
//                launch {
//                    dataStore.edit { settings ->
//                        settings[EXAMPLE_COUNTER] = false
//                        Log.d("CHANGING STATUS:",settings[EXAMPLE_COUNTER].toString())
//                    }
//
//                    dataStore.data.collect {
//                            value -> println("Collected $value")
//                    }
//
//
//                    dataStore.data.map {value -> Log.d("DATASTORE VALUES:",value.toString())
//                    }
//
//
////                    dataStore.data
////                        .map { preferences ->
////                            // No type safety.
////                            preferences[EXAMPLE_COUNTER] ?: 0
////                            Log.d("Collected2:",preferences[EXAMPLE_COUNTER].toString())
////                        }
//                }
//            }



        //}
        //}
    }

    private fun statustperfil(status:String) {
    }

    private fun iremergencia(){
        var irParaEmerg = Intent(this,Emergencia::class.java)
        startActivity(irParaEmerg)
    }
    companion object {

        private val TAG = "myProfile"

    }
    private fun hideSoftKeyboard(){
        val softKeyManager =
            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        softKeyManager.hideSoftInputFromWindow(binding.btnSalvarDados.windowToken, 0)
    }

    private fun updateUserInfo(data: HashMap<String, String?>): Task<String> {
        // Create the arguments to the callable function.


        return functions
            .getHttpsCallable("updateUserInfo")
            .call(data)
            .continueWith { task ->
                // This continuation runs on either success or failure, but if the task
                // has failed then result will throw an Exception which will be
                // propagated down.
                val result = task.result?.data as String
                result
            }
    }


    private fun onCreate(savedInstanceState: Bundle?, optionalParam: String? = null) {
        auth = Firebase.auth
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        var result:String = ""



        binding.btnSalvarDados.setOnClickListener { btnSalvarDados ->
            hideSoftKeyboard()
            val status = result.toString()
            val phoneNumber = binding.etTelefone.text.toString()
            val email = binding.etEmailEditarPerfil.text.toString()
            val nome = binding.etNomeEditarPerfil.text.toString()
            val endereco1 = binding.etEndereco1EditarPerfil.text.toString()
            val endereco2 = binding.etEndereco2EditarPerfil.text.toString()
            val endereco3 = binding.etEndereco3EditarPerfil.text.toString()
            val cv = binding.etCurriculoEditarPerfil.text.toString()

                   val user = Firebase.auth.currentUser
            user?.let {
                // Name, email address, and profile photo Url
                val uid = it.email

            }

            //singUpNewAccount(nome , email)
            val db = Firebase.firestore


            val usuario = hashMapOf(
                "userUid" to (user?.uid),
                "phoneNumber" to phoneNumber,
                "nome" to nome,
                "address1" to endereco1,
                "address2" to endereco2,
                "address3" to endereco3,
                "curriculum" to cv,
                "status" to status
            )
            updateUserInfo(usuario);
//            db.collection("usuarios").add(usuario)
//            Toast.makeText(baseContext,"Atualizado",Toast.LENGTH_LONG).show()
        }




    //    binding.switch1.setOnCheckedChangeListener { _, isChecked ->
      //      val result = if (isChecked) {
                // The switch is checked.
         //       "online"
           //     println("online")


            //} else {
                // The switch isn't checked.
              //  "offline"
            //}

        //}
        //Log.d("SWITCH STATUS", result.toString())
    }
}



//const userUid = data.userUid;
//const name = data.name;
//const phoneNumber = data.phoneNumber;
//const curriculum = data.curriculum;
//const status = data.name;
//const address1 = data.address1;
//const address2 = data.address2;
//const address3 = data.address3;