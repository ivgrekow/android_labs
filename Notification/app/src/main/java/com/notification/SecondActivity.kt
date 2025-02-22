package com.notification

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.Person
import java.util.Date

class SecondActivity: AppCompatActivity() {
    companion object {
        const val notificationId = 102
    }

    @SuppressLint("MissingPermission")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val button: Button = findViewById(R.id.button_second)

        val remoteViews = RemoteViews(packageName, R.layout.custom_view)

        button.setOnClickListener {
            val sender = Person.Builder()
                .setName("Мурзик")
                .build()

            val messagingStyle = NotificationCompat.MessagingStyle(sender)
                .addMessage("Хозяин, когда кормить будут?", Date().time, sender)

            val builder = NotificationCompat.Builder(this, "Cat channel")
                .setSmallIcon(R.drawable.cat)
                .setStyle(messagingStyle)
                /*.setCustomContentView(remoteViews)
                .setStyle(NotificationCompat.DecoratedCustomViewStyle())*/

            val channel = NotificationChannel(
                "Cat channel",
                "channel2", NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Feed cat"
            }

            with (NotificationManagerCompat.from(this)) {
                createNotificationChannel(channel)
                notify(notificationId, builder.build())
            }
        }
    }
}