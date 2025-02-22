package com.preferencesframework

import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity(), OnSharedPreferenceChangeListener {
    private lateinit var settingsLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val prefs = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        prefs.registerOnSharedPreferenceChangeListener(this)

        val button: Button = findViewById(R.id.button_open)
        val buttonRingtones: Button = findViewById(R.id.button_ringtones)
        val textView: TextView = findViewById(R.id.textView)

        settingsLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
            val catHeight = sharedPreferences.getInt("height", 20)
            //val prefsCheckBox: Boolean = sharedPreferences.getBoolean("wifi", true)
            textView.text = "Высота кота = $catHeight"
            //textView.text = prefsCheckBox.toString()
        }

        button.setOnClickListener {
            val intent = Intent(this@MainActivity, MyPreferenceActivity::class.java)
            settingsLauncher.launch(intent)
        }

        buttonRingtones.setOnClickListener {
            val intent = Intent(this@MainActivity, RingtonePreferenceActivity::class.java)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {
    }
}