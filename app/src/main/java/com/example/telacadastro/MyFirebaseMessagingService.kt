package com.example.telacadastro

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import android.content.Context
import androidx.core.app.NotificationManagerCompat

class MyFirebaseMessagingService :  FirebaseMessagingService(){
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: ${remoteMessage.from}")


        // Check if message contains a data payload.
        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: ${remoteMessage.data}")

            // Check if data needs to be processed by long running job
            if (needsToBeScheduled()) {
                // For long-running tasks (10 seconds or more) use WorkManager.
            } else {
                handleNow()
            }
        }

        // Check if message contains a notification payload.
        remoteMessage.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}")
            Toast.makeText(
                baseContext,
                "BODY: ${it.body}",
                Toast.LENGTH_SHORT,
            ).show()
            it.body?.let { it1 -> sendNotification(it1) }
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
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

        val name = getString(R.string.channel_name)
        val descriptionText = getString(R.string.channel_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel("1234",name,importance).apply {
            description = descriptionText
        }
        val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

        val builder = NotificationCompat.Builder(this,"FCMdefault")
            .setSmallIcon(androidx.core.R.drawable.notification_template_icon_bg)
            .setContentTitle("My notification")
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText(msg))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        notificationManager.createNotificationChannel(channel)
        val notificationID = 0;
        notificationManager.notify(notificationID,builder.build())
    }
    private fun createNotificationChannel(){
        val name = getString(R.string.channel_name)
        val descriptionText = getString(R.string.channel_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel("1234",name,importance).apply {
            description = descriptionText
        }
        val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)


    }

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