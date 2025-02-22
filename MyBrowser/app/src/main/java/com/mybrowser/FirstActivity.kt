package com.mybrowser

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

class FirstActivity: AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.first_activity)

        val button: Button = findViewById(R.id.button)

        button.setOnClickListener {
            val intent = Intent(this@FirstActivity, SecondActivity::class.java)
            intent.data = Uri.parse("https://ru.wikipedia.org")
            intent.action = Intent.ACTION_VIEW
            startActivity(Intent.createChooser(intent, "Гав..."))
        }
    }
}