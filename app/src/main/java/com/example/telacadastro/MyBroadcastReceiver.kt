package com.example.telacadastro

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.HttpsCallableResult
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase


class MyBroadcastReceiver : BroadcastReceiver() {
    private lateinit var functions: FirebaseFunctions
    private lateinit var auth: FirebaseAuth

    override fun onReceive(context: Context?, intent: Intent?) {

        val notificationManager: NotificationManager =
            context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        functions = Firebase.functions("southamerica-east1")
        auth = Firebase.auth
        val user = Firebase.auth.currentUser
        user?.let {
            val name = it.displayName
            val email = it.email
            val photoUrl = it.photoUrl

            // Check if user's email is verified
//            val emailVerified = it.isEmailVerified

            val uid = it.uid
        }


        // val notificationManager: NotificationManager = getSystemService(contextontext.NOTIFICATION_SERVICE) as NotificationManager

        fun acceptEmergency(emergency: String, uid: String): Task<HttpsCallableResult> {
             notificationManager.cancel(0)
            Log.d("FUNCTION", "START")
            val data = hashMapOf(
                "emergency" to emergency,
                "userName" to uid
            )
            return functions
                .getHttpsCallable("acceptEmergency")
                .call(data)
        }

        fun refuseEmergency(emergency: String, uid: String): Task<HttpsCallableResult> {
            notificationManager.cancel(0)
            Log.d("FUNCTION", "START")
            val data = hashMapOf(
                "emergency" to emergency,
                "userName" to uid
            )
            return functions
                .getHttpsCallable("refuseEmergency")
                .call(data)
        }


        val message = intent?.getStringExtra("MESSAGE")
        val uid = intent?.getStringExtra("emergencyUid").toString();
        val id = intent?.getStringExtra("notificationID").toString();


//
//        val intentExtras = intent?.extras
//            if (intentExtras != null) {
//                for (key in intentExtras.keySet()) {
//                    val value = intentExtras.get(key)
//                    Log.d("ALL INTENT EXTRAS", "$key: $value")
//                }
//            }

//        notificationManager.cancel(id.toInt())
        if (id == "acceptEmergency") {

            if (user != null) {
                acceptEmergency(uid, user.uid);
            }
            Log.d("channelID", id)
            Log.d(context.toString(), uid)
            Log.d(context.toString(), "new message")
            if (message != null) {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            }
        }

        if (id == "refuseEmergency") {

            if (user != null) {
                refuseEmergency(uid, user.uid);
            }
            Log.d("channelID", id)
            Log.d(context.toString(), uid)
            Log.d(context.toString(), "new message")
            if (message != null) {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            }
        }
    }



}




