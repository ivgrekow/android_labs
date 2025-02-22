package com.toast

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val duration = Toast.LENGTH_LONG
        val button: Button = findViewById(R.id.button_toast)
        val inflater = layoutInflater
        val container = findViewById<ViewGroup>(R.id.toast_container)
        val layout: View = inflater.inflate(R.layout.custom_toast, container)

        button.setOnClickListener {
            with (Toast(applicationContext)) {
                this.duration = duration
                view = layout
                addCallback(object: Toast.Callback() {
                    override fun onToastShown() {
                        super.onToastShown()
                        Log.d("Toast", "shown")
                    }

                    override fun onToastHidden() {
                        super.onToastHidden()
                        Log.d("Toast", "hidden")
                    }
                })
                show()
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}