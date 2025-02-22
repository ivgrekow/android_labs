package com.notification

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class MainActivity : AppCompatActivity() {
    companion object {
        const val NOTIFICATION_ID = 101
        const val CHANNEL_ID = "channelID"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val intent = Intent(this, SecondActivity::class.java)
        intent.apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        /*val link = "http://developer.alexanderklimov.ru/android"
        val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))*/

        val pendingIntent = PendingIntent.getActivity(this, 0, intent,
            PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        val button: Button = findViewById(R.id.button)
        val deleteButton: Button = findViewById(R.id.button_delete)

        button.setOnClickListener {
            val soundUri = Uri.parse(
                "android.resource://" +
                        applicationContext.packageName +
                        "/" + R.raw.sound)

            val audioAttributes = AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_ALARM)
                .build()
            val channel = NotificationChannel(CHANNEL_ID, "channel", NotificationManager.IMPORTANCE_HIGH)
            channel.vibrationPattern = longArrayOf(0, 1000, 100, 1000)
            channel.enableVibration(true)
            channel.setSound(soundUri, audioAttributes)

            val builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.cat)
                .setContentTitle(resources.getString(R.string.notify))
                //.setStyle(NotificationCompat.BigTextStyle().bigText("Когда кормить будут? Далее идёт очень длинный текст про бедного котика, которого морят голодом уже целых три минуты"))
                .setStyle(NotificationCompat.BigPictureStyle()
                    .bigPicture(BitmapFactory.decodeResource(resources, R.drawable.tiger))
                    .bigLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.cat))
                    .setBigContentTitle("Напоминание")
                    .setSummaryText("Пора кормить кота!"))
                /*.setStyle(NotificationCompat.InboxStyle()
                    .addLine("Первая")
                    .addLine("Вторая")
                    .addLine("Третья")
                    .addLine("Четвертая")
                    .addLine("Пятая")
                    .setBigContentTitle("Напоминание")
                    .setSummaryText("Пора кормить кота!"))*/
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.tiger))
                .setTicker("Последнее предупреждение!")
                .setProgress(100, 50, false)
                .setColor(Color.GREEN)
                .addAction(R.drawable.ic_launcher_background, "Открыть", pendingIntent)
                .addAction(R.drawable.ic_launcher_background_yellow, "Отказаться", pendingIntent)
                .addAction(R.drawable.ic_launcher_background_red, "Другой вариант", pendingIntent)

            val notificationManager = NotificationManagerCompat.from(this)
            notificationManager.createNotificationChannel(channel)
            notificationManager.notify(NOTIFICATION_ID, builder.build())
        }

        deleteButton.setOnClickListener {
            val notificationManager = NotificationManagerCompat.from(this)
            notificationManager.cancel(NOTIFICATION_ID)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}