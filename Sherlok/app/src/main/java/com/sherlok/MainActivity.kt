package com.sherlok

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    companion object {
        const val REQUEST_CHOOSE_THIEF = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val buttonChoice: Button = findViewById(R.id.button_choice)

        buttonChoice.setOnClickListener {
            val questionIntent = Intent(this@MainActivity, SecondActivity::class.java)
            startActivityForResult(questionIntent, REQUEST_CHOOSE_THIEF)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val textView: TextView = findViewById(R.id.textview_info)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CHOOSE_THIEF -> {
                    val thiefName = data?.getStringExtra(THIEF)
                    textView.text = thiefName.toString()
                }
            }
        } else {
            textView.text = ""
        }
    }
}