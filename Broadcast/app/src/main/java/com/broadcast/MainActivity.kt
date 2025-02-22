package com.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class MainActivity : AppCompatActivity() {
    companion object {
        val WHERE_MY_CAT_ACTION: String = "com.broadcast.action.CAT"
        val ALARM_MESSAGE: String = "Срочно пришлите кота!"

        private fun getCurrentTimeStamp(): String {
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US)
            val now = Date()
            return simpleDateFormat.format(now)
        }
    }

    private lateinit var messageReceiver: MessageReceiver
    private val timeBroadcastReceiver = TimeBroadcastReceiver()
    private val tickReceiver by lazy { makeBroadcastReceiver() }
    private lateinit var dateTimeTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val button: Button = findViewById(R.id.send_message)
        val button2: Button = findViewById(R.id.send_message2)
        dateTimeTextView = findViewById(R.id.time)

        val filter = IntentFilter(WHERE_MY_CAT_ACTION)
        val filterPower = IntentFilter("android.intent.action.ACTION_POWER_DISCONNECTED")
        messageReceiver = MessageReceiver()
        registerReceiver(messageReceiver, filter, RECEIVER_NOT_EXPORTED)
        registerReceiver(messageReceiver, filterPower, RECEIVER_NOT_EXPORTED)

        button.setOnClickListener {
            val intent = Intent()
            intent.action = WHERE_MY_CAT_ACTION
            intent.putExtra("com.broadcast.Message", ALARM_MESSAGE)
            intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES)
            sendBroadcast(intent)
        }

        button2.setOnClickListener {
            registerReceiver(
                timeBroadcastReceiver, IntentFilter("android.intent.action.TIME_TICK")
            )
            Toast.makeText(applicationContext, "Приемник включен", Toast.LENGTH_SHORT).show()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onResume() {
        super.onResume()

        dateTimeTextView.text = getCurrentTimeStamp()
        registerReceiver(tickReceiver, IntentFilter(Intent.ACTION_TIME_TICK))
    }

    override fun onPause() {
        super.onPause()

        try {
            unregisterReceiver(tickReceiver)
        } catch (e: IllegalArgumentException) {
            Log.e("Broadcast", "Time tick Receiver not registered", e)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        unregisterReceiver(messageReceiver)
        unregisterReceiver(timeBroadcastReceiver)
        Toast.makeText(applicationContext, "Приемник выключен", Toast.LENGTH_SHORT).show()
    }

    private fun makeBroadcastReceiver(): BroadcastReceiver {
        return object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (intent?.action == Intent.ACTION_TIME_TICK) {
                    dateTimeTextView.text = getCurrentTimeStamp()
                }
            }
        }
    }
}