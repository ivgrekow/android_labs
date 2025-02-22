package com.texteditor

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.File

class MainActivity : AppCompatActivity() {
    private val FILENAME = "sample.txt"
    private lateinit var editText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        editText = findViewById(R.id.editText)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onResume() {
        super.onResume()

        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val fontSize = prefs.getString(getString(R.string.pref_size), "20")!!.toFloat()
        val regular = prefs.getString(getString(R.string.pref_style), "")
        var typeface = Typeface.NORMAL
        val regularColor = prefs.getString(getString(R.string.pref_color), "Красный")

        if (regular!!.contains("Полужирный")) {
            typeface += Typeface.BOLD
        }

        if (regular.contains("Курсив")) {
            typeface += Typeface.ITALIC
        }

        val color: Int = when (regularColor) {
            "Синий" -> Color.BLUE
            "Зеленый" -> Color.GREEN
            "Желтый" -> Color.YELLOW
            else -> Color.RED
        }

        editText.textSize = fontSize
        editText.setTextColor(color)
        editText.setTypeface(null, typeface)

        if (prefs.getBoolean(getString(R.string.pref_openmode), false)) {
            openFile(FILENAME)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_open -> {
                openFile(FILENAME)
                true
            }
            R.id.action_save -> {
                saveFile(FILENAME)
                true
            }
            R.id.action_settings -> {
                val intent = Intent(this@MainActivity, SettingsActivity::class.java)
                startActivity(intent)
                true
            }
            else -> true
        }
    }

    private fun openFile(filename: String) {
        val textFromFile = File(applicationContext.filesDir, filename)
            .bufferedReader()
            .use { it.readText() }

        editText.setText(textFromFile)
    }

    private fun saveFile(filename: String) {
        applicationContext.openFileOutput(filename, Context.MODE_PRIVATE).use {
            it.write(editText.text.toString().toByteArray())
        }

        Toast.makeText(applicationContext, "Файл сохранен", Toast.LENGTH_SHORT).show()
    }
}