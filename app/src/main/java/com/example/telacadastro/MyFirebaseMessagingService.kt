package com.example.telacadastro

import android.app.Notification.EXTRA_NOTIFICATION_ID
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage


class MyFirebaseMessagingService :  FirebaseMessagingService(){

    lateinit var storage: FirebaseStorage
    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: ${remoteMessage.from}")



        // Check if message contains a data payload.
        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: ${remoteMessage.data}")
            Log.d(TAG,"UID: ${remoteMessage.data["uid"]}")
            remoteMessage.data["uid"]?.let { sendNotification(it) }
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
    // [END on_new_token]

//    private fun scheduleJob() {
//        // [START dispatch_job]
//        val work = OneTimeWorkRequest.Builder(MyWorker::class.java)
//            .build()
//        WorkManager.getInstance(this)
//            .beginWith(work)
//            .enqueue()
//        // [END dispatch_job]
//    }

    private fun handleNow() {
        Log.d(TAG, "Short lived task is done.")
    }

    private fun sendRegistrationToServer(token: String?) {
        // TODO: Implement this method to send token to your app server.
        Log.d(TAG, "sendRegistrationTokenToServer($token)")
    }

    private fun sendNotification(msg:String){
        storage = Firebase.storage


        var storageRef = storage.reference

        var emergencias: StorageReference? = storageRef.child("emergencias/1")

      //  var spaceRef = storageRef.child("1/")

        var orangutan = emergencias?.child("orangutan_square-763017175.jpg")



        val ONE_MEGABYTE: Long = 1024 * 1024

        orangutan?.getBytes(ONE_MEGABYTE)?.addOnSuccessListener {image->
            val bitmap = BitmapFactory.decodeByteArray(image, 0, image.size)
            Log.d("IMAGE","IMAGE RECEIVED")
        }?.addOnFailureListener {
            // Handle any errors
        }

        val httpsReference = storage.getReferenceFromUrl(
            "https://firebasestorage.googleapis.com/v0/b/teethkids-10c6a.appspot.com/o/emergencias%2F1%2Forangutan_square-763017175.jpg?alt=media&token=4eede945-cad9-46da-a193-f3dc7f1123ee"
        )

        val name = getString(R.string.channel_name)
        val descriptionText = getString(R.string.channel_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(name,name,importance).apply {
            description = descriptionText
        }
        val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

        val intent = Intent(this, MyBroadcastReceiver::class.java)
            .putExtra("emergencyUid",msg )
            .putExtra("notificationID","emergencias")
            .apply { action = "com.example.ACTION_LOG" }

        //intent.putExtra("emergencyUid",msg)
        val intentExtras = intent.extras
        if (intentExtras != null) {
            for (key in intentExtras.keySet()) {
                val value = intentExtras.get(key)
                Log.d("IntentExtras", "$key: $value")
            }
        }

        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getBroadcast(this, 0, intent,   PendingIntent.FLAG_IMMUTABLE )

        } else {
            TODO("VERSION.SDK_INT < S")
        } // setting the mutability flag )
        

        val builder = NotificationCompat.Builder(this,getString(R.string.channel_name))
            .setSmallIcon(androidx.core.R.drawable.notification_template_icon_bg)
            .setContentTitle("NEW emergency")
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText(msg))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .addAction(R.drawable.ic_action_name, "Aceitar Emergência?",pendingIntent)

        notificationManager.createNotificationChannel(channel)
        val notificationID = 0;
        notificationManager.notify(notificationID,builder.build())
    }

//    private fun createNotificationChannel(){
//        val name = getString(R.string.channel_name)
//        val descriptionText = getString(R.string.channel_description)
//        val importance = NotificationManager.IMPORTANCE_DEFAULT
//        val channel = NotificationChannel("1234",name,importance).apply {
//            description = descriptionText
//        }
//        val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        notificationManager.createNotificationChannel(channel)
//
//
//    }

//    private fun sendNotification(messageBody: String) {
//        val intent = Intent(this, MainActivity::class.java)
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//        val requestCode = 0
//        val pendingIntent = PendingIntent.getActivity(
//            this,
//            requestCode,
//            intent,
//            PendingIntent.FLAG_IMMUTABLE,
//        )
//
//        val channelId = "fcm_default_channel"
//        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
//        val notificationBuilder = NotificationCompat.Builder(this, channelId)
//            .setSmallIcon(R.mipmap.ic_launcher)
//            .setContentTitle("FCM Message")
//            .setContentText(messageBody)
//            .setAutoCancel(true)
//            .setSound(defaultSoundUri)
//            .setContentIntent(pendingIntent)
//
//        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//
//        // Since android Oreo notification channel is needed.
//        val channel = NotificationChannel(
//            channelId,
//            "Channel human readable title",
//            NotificationManager.IMPORTANCE_DEFAULT,
//        )
//        notificationManager.createNotificationChannel(channel)
//
//        val notificationId = 0
//        notificationManager.notify(notificationId, notificationBuilder.build())
//    }

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