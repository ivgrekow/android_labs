package com.orientation

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.view.Surface
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var button: Button
    val ORIENTATION_PORTRAIT: String = "Портретный режим"
    val ORIENTATION_LANDSCAPE: String = "Альбомный режим"
    private var state: Boolean = false

    private fun getScreenOrientation(): String {
        return when (resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> "Портретная ориентация"
            Configuration.ORIENTATION_LANDSCAPE -> "Альбомная ориентация"
            else -> ""
        }
    }

    private fun getRotateOrientation(): String {
        return when (windowManager.defaultDisplay.rotation) {
            Surface.ROTATION_0 -> "Не поворачивали"
            Surface.ROTATION_90 -> "Повернули на 90 по чс"
            Surface.ROTATION_180 -> "Повернули на 180"
            Surface.ROTATION_270 -> "Повернули на 90 против чс"
            else -> "Не понятно"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val input: EditText = findViewById(R.id.input)
        button = findViewById(R.id.button1)

        button.text = ORIENTATION_LANDSCAPE

        button.setOnClickListener {
            if (!state) {
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                button.text = ORIENTATION_PORTRAIT
            } else {
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                button.text = ORIENTATION_LANDSCAPE
            }

            state = !state
        }

        input.setText(getScreenOrientation() + " " + getRotateOrientation())
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        val button2: Button = findViewById(R.id.button2)

        val orientation = when (newConfig.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> "Portrait"
            Configuration.ORIENTATION_LANDSCAPE -> "Landscape"
            Configuration.ORIENTATION_UNDEFINED -> "Undefined"
            else -> "Error"
        }

        button2.text = orientation
    }
}