package com.example.koskosan.firebase

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_ONE_SHOT
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.koskosan.R
import com.example.koskosan.chat.ChatFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlin.random.Random




class FirebaseService: FirebaseMessagingService() {

    companion object {
        private const val  CHANNEL_ID = "my_channel"
    }

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)

        val user = FirebaseAuth.getInstance().currentUser?.displayName



        val intent = Intent(this, ChatFragment::class.java)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as  NotificationManager
        val  notificationID = Random.nextInt()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager)
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent , FLAG_ONE_SHOT)
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(p0.data["title"])
            .setContentText("$user anda mendapatkan pesan baru")
            .setSmallIcon(R.drawable.ic_notif)
            .setAutoCancel(true)
            .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.ic_notif))
            .setVibrate(longArrayOf(1000,1000,1000,1000,1000))
            .setDefaults(Notification.DEFAULT_SOUND)
            .setContentIntent(pendingIntent)
            .build()


        notificationManager.notify(notificationID, notification)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val ChannelName = "Channel name"
        val channel =  NotificationChannel(CHANNEL_ID, ChannelName, IMPORTANCE_HIGH).apply {
            description = "My Channel description"
            enableLights(true)
            lightColor = Color.YELLOW
        }

        notificationManager.createNotificationChannel(channel)

        }
    }
