package com.androidlesson

import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import android.widget.TextView

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView: TextView = findViewById(R.id.text)

        textView.setOnClickListener {
            val builder: AlertDialog.Builder = AlertDialog.Builder(this, R.style.AlertDialogCustom_Destructive)
            builder.setTitle("Dialog")
            builder.setMessage("Покормить кота?")
            builder.setPositiveButton("OK", null)
            builder.setNegativeButton("Cancel", null)
            builder.show()
        }
    }
}