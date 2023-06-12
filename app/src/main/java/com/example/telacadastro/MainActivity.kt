package com.example.telacadastro

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import com.example.telacadastro.databinding.ActivityMainBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.util.prefs.Preferences


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var storage: FirebaseStorage? = null

    private  lateinit var dataStore: DataStore<Preferences>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.BtnEntrar.setOnClickListener { bentrar ->
            irParaTelaLogin()
        }
        binding.BtnCriarConta.setOnClickListener { bcriarconta ->
            irParaCriarConta()
        }

        storage = Firebase.storage
        askNotificationPermission()
        val user = Firebase.auth.currentUser
        if (user != null) {

            var homeScreen = Intent(this, Tela_Inicial::class.java)
            startActivity(homeScreen)
        } else {
            // No user is signed in
        }

    }


    private fun irParaCriarConta() {
        var criarcontaTela = Intent(this, criarConta::class.java)
        startActivity(criarcontaTela)
    }

    private  fun alreadySignIn()
    {
        val user = Firebase.auth.currentUser
        if (user != null) {
            // User is signed in
        } else {
            // No user is signed in
        }
    }

    private fun irParaTelaLogin() {
        var telaLog = Intent(this, telaLogin::class.java)
        startActivity(telaLog)
    }


    // Declare the launcher at the top of your Activity/Fragment:
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) {
            // FCM SDK (and your app) can post notifications.
        } else {
            // TODO: Inform user that that your app will not show notifications.
        }
    }

    private fun askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {

            } else {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }


    var builder = NotificationCompat.Builder(this, "emergencyAlert")
        .setSmallIcon(R.drawable.heart_circle_plus_solid)
        .setContentTitle("nova emergência")
        .setContentText("aceitar emergência?")
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)


}