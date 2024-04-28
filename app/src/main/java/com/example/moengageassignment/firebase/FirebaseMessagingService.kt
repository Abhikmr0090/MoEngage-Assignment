package com.example.moengageassignment.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

const val CHANNEL_ID = "121"
const val CHANNEL_NAME = "notification_channel"

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    /**
     * onMessageReceived will we called whenever the notification
     * sent from the Firebase Cloud Messaging GUI or from server
     */
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        /**
         * From remoteMessage.notification we can access title and body from the json payload
         *
         * Ex : {
         *      "remoteMessage": {
         *         "notification" : {
         *             "title" : "Your Notification Title",
         *             "body"  : "Your Notification Body"
         *         }
         *      }
         * }
         */
        remoteMessage.notification?.let {

        }

        /**
         * From remoteMessage.data we can get the data from the json payload
         * you can access the data from data payload using the key
         *  Ex: {
         *      "remoteMessage" : {
         *         "data" : {
         *            "your_key" : "your_data"
         *         }
         *      }
         *  }
         */
        remoteMessage.data.let { map ->
            // val data = map["your_key"]
        }

        Log.d("REMOTE_MSG", "onMessageReceived: ${remoteMessage.notification}")

        // Showing Notification when the Application is in Foreground state
        showNotification(remoteMessage)
    }

    private fun showNotification(remoteMessage: RemoteMessage) {
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        val builder = remoteMessage.notification?.let { notification ->
            NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(notification.title ?: "")
                .setContentText(notification.body ?: "")
                .setAutoCancel(true)
        }

        // Added the notification channel to handle notifications after Android 8 version
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }

        notificationManager.notify(0, builder?.build())

    }

}