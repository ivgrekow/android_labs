package com.alertdialog

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import java.util.Timer
import java.util.TimerTask

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val button: Button = findViewById(R.id.button_alert)
        val buttonTwo: Button = findViewById(R.id.button_alert_two)
        val buttonList: Button = findViewById(R.id.button_alert_list)
        val buttonListFlags: Button = findViewById(R.id.button_alert_list_flags)
        val buttonRating: Button = findViewById(R.id.button_alert_rating)

        button.setOnClickListener {
            val dialogFragment = MyDialogFragment()
            val manager = supportFragmentManager
            dialogFragment.show(manager, "myDialog")
        }

        buttonTwo.setOnClickListener {
            val dialogFragment = MySecondDialogFragment()
            val manager = supportFragmentManager
            dialogFragment.show(manager, "myDialogTwo")
        }

        buttonList.setOnClickListener {
            val dialogFragment = MyThirdDialogFragment()
            val manager = supportFragmentManager
            dialogFragment.show(manager, "myDialogList")
        }

        buttonListFlags.setOnClickListener {
            val dialogFragment = MyFourthDialogFragment()
            val manager = supportFragmentManager
            dialogFragment.show(manager, "myDialogListFlags")

            val timer = Timer()
            timer.schedule(object : TimerTask() {
                override fun run() {
                    dialogFragment.dismiss()
                    timer.cancel()
                }
            }, 5000)
        }

        buttonRating.setOnClickListener {
            val dialogFragment = MyFifthDialogFragment()
            val manager = supportFragmentManager
            dialogFragment.show(manager, "myDialogRating")
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun ok() {
        Snackbar.make(findViewById(R.id.main), "Вы выбрали кнопку ОК!", Snackbar.LENGTH_SHORT)
            .show()
    }

    fun cancel() {
        Snackbar.make(findViewById(R.id.main), "Вы выбрали кнопку отмены!", Snackbar.LENGTH_SHORT)
            .show()
    }
}