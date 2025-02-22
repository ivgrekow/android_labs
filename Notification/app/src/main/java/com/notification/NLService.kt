package com.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log

class NLService: NotificationListenerService() {
    /*private val TAG = this::class.simpleName
    private lateinit var receiver: NLServiceReceiver

    override fun onCreate() {
        super.onCreate()
        receiver = NLServiceReceiver()
        val filter = IntentFilter()
        filter.addAction("ru.NOTIFICATION_LISTENER_SERVICE_EXAMPLE")
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        Log.i(TAG, "onNotificationPosted")
        Log.i(TAG, "ID: " + sbn.id + "\t" + sbn.notification.tickerText + "\t" + sbn.packageName)
        val intent = Intent("ru.NOTIFICATION_LISTENER_SERVICE_EXAMPLE")
        intent.putExtra("notification_event", "onNotificationPosted:\n" + sbn.packageName + "\n")
        sendBroadcast(intent)
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification) {
        Log.i(TAG, "onNotificationRemoved")
        Log.i(TAG, "ID: " + sbn.id + "\t" + sbn.notification.tickerText + "\t" + sbn.packageName)
        val intent = Intent("ru.NOTIFICATION_LISTENER_SERVICE_EXAMPLE")
        intent.putExtra("notification_event", "onNotificationRemoved:\n" + sbn.packageName + "\n")
        sendBroadcast(intent)
    }
}

class NLServiceReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.getStringExtra("command").equals("clearall")) {
        } else if (intent.getStringExtra("command").equals("list")) {
            val notificationIntent = Intent("ru.NOTIFICATION_LISTENER_SERVICE_EXAMPLE")
            notificationIntent.putExtra("notification_event", "=======")
            sendBroadcast()
        }
    }*/
}