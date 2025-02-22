package com.android_app

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        var user: String? = "Жывотное"
        var from: String? = "кого-то"
        var gift: String? = "дырку от бублика"
        val textView: TextView = findViewById(R.id.text_second_info)

        user = intent.getStringExtra("username")
        from = intent.getStringExtra("from")
        gift = intent.getStringExtra("gift")

        textView.text = user + " , вам передали " + gift + ". От " + from
    }
}