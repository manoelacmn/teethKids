package com.example.telacadastro

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.telacadastro.databinding.ActivityMainBinding
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


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
    }


    private fun irParaCriarConta() {
        var criarcontaTela = Intent(this, criarConta::class.java)
        startActivity(criarcontaTela)
    }

    private fun irParaTelaLogin() {
        var telaLog = Intent(this, telaLogin::class.java)
        startActivity(telaLog)
    }


//    private fun createNotificationChannel() {
//        // Create the NotificationChannel, but only on API 26+ because
//        // the NotificationChannel class is new and not in the support library
//        val name = getString(R.string.channel_name)
//        val descriptionText = getString(R.string.channel_description)
//        val importance = NotificationManager.IMPORTANCE_DEFAULT
//        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
//            description = descriptionText
//        }
//        // Register the channel with the system
//        val notificationManager: NotificationManager =
//            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        notificationManager.createNotificationChannel(channel)
//    }


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



    var builder = NotificationCompat.Builder(this, "emergencyAlert")
        .setSmallIcon(R.drawable.heart_circle_plus_solid)
        .setContentTitle("nova emergência")
        .setContentText("aceitar emergência?")
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//        .addAction(R.drawable.ic_snooze, getString(R.string.snooze),
//            snoozePendingIntent)


}