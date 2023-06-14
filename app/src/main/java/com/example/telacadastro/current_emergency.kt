package com.example.telacadastro

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import com.example.telacadastro.databinding.ActivityCurrentEmergencyBinding
import com.example.telacadastro.databinding.ActivityMainBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.util.prefs.Preferences


class current_emergency : AppCompatActivity() {

    private lateinit var functions: FirebaseFunctions

    private lateinit var binding: ActivityCurrentEmergencyBinding
    var storage: FirebaseStorage? = null

    private lateinit var dataStore: DataStore<Preferences>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCurrentEmergencyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = Firebase.auth.currentUser

        functions = Firebase.functions("southamerica-east1")


        fun isBusy(uid: String): Task<String> {
            // Create the arguments to the callable function.
            val data = hashMapOf(
                "userUid" to uid,
            )

            return functions
                .getHttpsCallable("isBusy")
                .call(data)
                .continueWith { task ->
                    // This continuation runs on either success or failure, but if the task
                    // has failed then result will throw an Exception which will be
                    // propagated down.
                    val result = task.result?.data as String
                    Log.d("RESULT:",result)
                    result
                }


        }
        user?.let {

            val uid = it.uid
        }
        isBusy(user!!.uid)

    }

}


