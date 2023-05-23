package com.example.telacadastro

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.startActivity
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

    private var message: String? = null
    var uid: String? = null
    var id: String? = null
    var nome: String? = null
    private var furry: String? = null

    override fun onReceive(context: Context?, intent: Intent?) {
         furry =  null;
        nome = null
        id = null
        message = null
        uid = null

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


         message = intent?.getStringExtra("MESSAGE")
         uid = intent?.getStringExtra("emergencyUid").toString();
         id = intent?.getStringExtra("notificationID").toString();
         nome = intent?.getStringExtra("nome").toString()
        furry = intent?.getStringExtra("FURRY").toString()

        Log.d("NOME ON BRODCAST:", nome!!)
        Log.d("NOME ON BRODCAST:", intent?.getStringExtra("nome").toString())
        Log.d("FURRY", furry!!)
        Log.d("UID ON BROADCAST:", uid!!)
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
//            val newIntent = Intent(context,perfil_socorrista::class.java)
//                .putExtra("emergencyUid",uid)
//                .putExtra("nome",nome)

            val newIntent = Intent(context, perfil_socorrista::class.java).apply {
                putExtra("emergencyUid", uid)
                putExtra("nome", nome)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }

            context.startActivity(newIntent)
            notificationManager.cancel(0)

//            if (user != null) {
//                acceptEmergency(uid, user.uid);
//            }
            Log.d("channelID", id!!)
            Log.d(context.toString(), uid!!)
            Log.d(context.toString(), "new message")
            if (message != null) {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            }
        }

        if (id == "refuseEmergency") {

            if (user != null) {
                refuseEmergency(uid!!, user.uid);
            }
            Log.d("channelID", id!!)
            Log.d(context.toString(), uid!!)
            Log.d(context.toString(), "new message")
//            if (message != null) {
//                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
//            }
        }
    }



}




