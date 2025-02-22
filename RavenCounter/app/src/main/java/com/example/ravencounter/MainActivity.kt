package com.example.ravencounter

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private var counter: Int = 0
    private var counterCats: Int = 0
    private val KEY_COUNT: String = "COUNT"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val textView: TextView = findViewById(R.id.textView)
        val button: Button = findViewById(R.id.button)
        val buttonCounter: Button = findViewById(R.id.button_counter)
        val buttonCounterCats: Button = findViewById(R.id.button_counter_cats)

        if (savedInstanceState != null) {
            counter = savedInstanceState.getInt(KEY_COUNT, 0)
            textView.text = "Я насчитал ${++counter} ворон"
        }

        button.setOnClickListener {
            textView.text = "Hello Kitty!"
            it.setBackgroundColor(Color.BLUE)
        }

        buttonCounter.setOnClickListener {
            textView.text = "Я насчитал ${++counter} ворон"
        }

        buttonCounterCats.setOnClickListener {
            textView.text = "Я насчитал ${++counterCats} котов"
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt(KEY_COUNT, counter)
    }
}