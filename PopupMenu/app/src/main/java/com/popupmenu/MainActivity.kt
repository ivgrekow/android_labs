package com.popupmenu

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
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

        val button: Button = findViewById(R.id.button)
        val textView: TextView = findViewById(R.id.textView)
        val imageView: ImageView = findViewById(R.id.imageView)

        val popupMenu = PopupMenu(this, button)
        popupMenu.inflate(R.menu.popup_menu)

        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.red -> {
                    textView.background = ColorDrawable(Color.RED)
                    textView.text = "Вы выбрали красный цвет!"
                    true
                }
                R.id.yellow -> {
                    textView.background = ColorDrawable(Color.YELLOW)
                    textView.text = "Вы выбрали желтый цвет!"
                    true
                }
                R.id.green -> {
                    textView.background = ColorDrawable(Color.GREEN)
                    textView.text = "Вы выбрали зеленый цвет!"
                    true
                }
                else -> false
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            popupMenu.setForceShowIcon(true)
        }

        button.setOnClickListener {
            popupMenu.show()
        }

        imageView.setOnClickListener {
            val popup = PopupMenu(this, it)
            popup.menu.add(1, R.id.item1, 1, "slot1")
            popup.menu.add(1, R.id.item2, 2, "slot2")
            popup.show()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun showPopupMenu(v: View) {
        val popupMenu = PopupMenu(this, v)
        popupMenu.inflate(R.menu.popupmenu)

        popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.item1 -> {
                        Toast.makeText(applicationContext,
                            "Вы выбрали PopupMenu 1", Toast.LENGTH_SHORT).show()
                        true
                    }
                    R.id.item2 -> {
                        Toast.makeText(applicationContext,
                            "Вы выбрали PopupMenu 2", Toast.LENGTH_SHORT).show()
                        true
                    }
                    R.id.item3 -> {
                        Toast.makeText(applicationContext,
                            "Вы выбрали PopupMenu 3", Toast.LENGTH_SHORT).show()
                        true
                    }
                    else -> false
                }
        }

        popupMenu.setOnDismissListener {
            Toast.makeText(applicationContext, "onDismiss", Toast.LENGTH_SHORT).show()
        }

        popupMenu.show()
    }
}