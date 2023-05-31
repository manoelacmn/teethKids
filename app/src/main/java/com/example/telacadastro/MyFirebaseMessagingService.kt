package com.example.telacadastro

import android.annotation.SuppressLint
import android.app.Notification
import android.app.Notification.EXTRA_NOTIFICATION_ID
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.core.app.NotificationManagerCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import java.util.prefs.Preferences


class MyFirebaseMessagingService :  FirebaseMessagingService(){

    lateinit var storage: FirebaseStorage

    // private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings");

    val EXAMPLE_COUNTER = booleanPreferencesKey("isAvalible")



//    val exampleCounterFlow: Flow<Boolean> = dataStore.data
//        .map { preferences ->
//            // No type safety.
//            preferences[EXAMPLE_COUNTER] ?: 0
//        } as Flow<Boolean>



    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: ${remoteMessage.from}")


        // Check if message contains a data payload.
        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: ${remoteMessage.data}")
            Log.d(TAG,"UID: ${remoteMessage.data["uid"]}")
            Log.d(TAG,"NOME: ${remoteMessage.data["nome"]}")
            Log.d(TAG,"IMAGE PATH: ${remoteMessage.data["ImageRoot"]}" )

//            val nome = remoteMessage.data["nome"]}

            remoteMessage.data["uid"]?.let { remoteMessage.data["nome"]?.let { it1 ->
                remoteMessage.data["ImageRoot"]?.let { it2 ->
                    sendNotification(it,
                        it1 , it2
                    )
                }
            } }

            // Check if data needs to be processed by long running job
            if (needsToBeScheduled()) {
                // For long-running tasks (10 seconds or more) use WorkManager.
            } else {
                handleNow()
            }
        }
    }
    // [END receive_message]

    private fun needsToBeScheduled() = true

    // [START on_new_token]
    /**
     * Called if the FCM registration token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the
     * FCM registration token is initially generated so this is where you would retrieve the token.
     */
    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
        sendRegistrationToServer(token)
    }


    private fun handleNow() {
        Log.d(TAG, "Short lived task is done.")
    }

    private fun sendRegistrationToServer(token: String?) {
        // TODO: Implement this method to send token to your app server.
        Log.d(TAG, "sendRegistrationTokenToServer($token)")
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun sendNotification(msg:String, nome:String, ImageRoot:String){
        storage = Firebase.storage

        Log.d("DEFINITVE NOME",nome)

        var storageRef = storage.reference

        var emergencias: StorageReference? = storageRef.child("emergencias/1")

        //  var spaceRef = storageRef.child("1/")


        val imageRef = storageRef.child(ImageRoot)

        val ONE_MEGABYTE: Long = 1024 * 1024

        var monkey: Bitmap? = null

        imageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener { image->
            monkey = BitmapFactory.decodeByteArray(image, 0, image.size)
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(name,name,importance).apply {
                description = descriptionText }

            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)




            var intent = Intent(this, MyBroadcastReceiver::class.java).apply {
                putExtra("emergencyUid", msg)
                putExtra("nome", nome)
                putExtra("imagePath",ImageRoot)
                putExtra("notificationID", "acceptEmergency")
    //                apply { action = "com.example.ACTION_LOG" }
            }

            var refuseIntentExtras = Intent(this, perfil_socorrista::class.java).apply {

                putExtra("nome", nome)
                putExtra("emergencyUid", msg)
                putExtra("notificationID", "refuseEmergency")
                apply { action = "com.example.ACTION_LOG" }
            }
            val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                PendingIntent.getBroadcast(this, 0, intent,   PendingIntent.FLAG_IMMUTABLE)



            } else {
                // TODO("VERSION.SDK_INT < S")
                PendingIntent.getBroadcast(this, 0, intent,   PendingIntent.FLAG_ONE_SHOT)
            }
//            pendingIntent.cancel()
            // setting the mutability flag )

            val refuseIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                PendingIntent.getBroadcast(this, 1, refuseIntentExtras,   PendingIntent.FLAG_IMMUTABLE )

            } else {
    //                TODO("VERSION.SDK_INT < S")
                PendingIntent.getBroadcast(this, 1, refuseIntentExtras,   PendingIntent.FLAG_IMMUTABLE )
            }
            val builder = NotificationCompat.Builder(this,getString(R.string.channel_name))
                .setSmallIcon(R.drawable.baseline_healing_24)
                .setContentTitle("NEW emergency")
                .setStyle(NotificationCompat.BigPictureStyle().bigPicture(monkey))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .addAction(R.drawable.ic_action_name, "Aceitar Emergência?",pendingIntent)
                .addAction(0,"RECUSAR",refuseIntent)
                .setAutoCancel(true)


            notificationManager.createNotificationChannel(channel)
            val notificationID = 0;
            notificationManager.notify(notificationID,builder.build())

        }.addOnFailureListener {
            // Handle any errors
        }



    }



    companion object {
        private const val TAG = "MyFirebaseMsgService"
    }

//    internal class MyWorker(appContext: Context, workerParams: WorkerParameters) : Worker(appContext, workerParams) {
//        override fun doWork(): Result {
//            // TODO(developer): add long running task here.
//            return Result.success()
//        }
//    }
}
