package com.example.telacadastro

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import com.example.telacadastro.databinding.ActivityCurrentEmergencyBinding
import com.example.telacadastro.databinding.ActivityMainBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.util.prefs.Preferences


class current_emergency : AppCompatActivity() {

    private lateinit var binding: ActivityCurrentEmergencyBinding
    var storage: FirebaseStorage? = null

    private lateinit var dataStore: DataStore<Preferences>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCurrentEmergencyBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }

}


