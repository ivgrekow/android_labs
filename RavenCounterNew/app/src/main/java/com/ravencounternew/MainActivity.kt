package com.ravencounternew

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private var count = 0
    private lateinit var prefs: SharedPreferences
    private val APP_PREFERENCES_COUNTER = "counter"
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        prefs = getSharedPreferences("settings", Context.MODE_PRIVATE)

        val button: Button = findViewById(R.id.button_counter)
        val buttonReset: Button = findViewById(R.id.button_reset)
        textView = findViewById(R.id.textView)

        button.setOnClickListener {
            count++
            textView.text = "${resources.getString(R.string.ravens)} $count"
        }

        buttonReset.setOnClickListener {
            count = 0
            prefs.edit().remove(APP_PREFERENCES_COUNTER).apply()
            textView.text = "${resources.getString(R.string.ravens)} $count"
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onPause() {
        super.onPause()

        val editor = prefs.edit()
        editor.putInt(APP_PREFERENCES_COUNTER, count).apply()
    }

    override fun onResume() {
        super.onResume()

        if (prefs.contains(APP_PREFERENCES_COUNTER)) {
            count = prefs.getInt(APP_PREFERENCES_COUNTER, 0)
            textView.text = "${resources.getString(R.string.ravens)} $count"
        }
    }
}