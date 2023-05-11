package com.example.telacadastro

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
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



        val message = intent?.getStringExtra("MESSAGE")
        val uid = intent?.getStringExtra("emergencyUid").toString();
        val id = intent?.getStringExtra("notificationID").toString();

        if (user != null) {
            acceptEmergency(uid,user.uid);
        }

        Log.d("channelID",id)
        Log.d(context.toString(),uid)
        Log.d(context.toString(),"new message")
        if (message!=null){
            Toast.makeText(context,message,Toast.LENGTH_LONG).show()
        }


    }


// TOOO change uid to username
    private fun acceptEmergency(emergency: String, uid: String): Task<HttpsCallableResult> {
        Log.d("FUNCTION","START")
        val data = hashMapOf(
            "emergency" to emergency,
            "userName" to uid
        )
        return functions
            .getHttpsCallable("acceptEmergency")
            .call(data)
    }

//    export const acceptEmergency = functions.region("southamerica-east1").https.onCall(async (data, context) => {
//        const emergency = data.emergency.toString();
//        // const uid = context.auth?.uid;
//        const userName = data.userName.toString();
//        const emergencyDocRef = db.collection("emergency").doc(emergency);
//        (await emergencyDocRef.update({status: "accepted", acceptedBy: userName}));
//    });

}




