package com.screen

import android.content.Intent
import android.graphics.Point
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.provider.Settings.SettingNotFoundException
import android.util.DisplayMetrics
import android.util.Log
import android.view.Display
import android.view.WindowManager
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val button: Button = findViewById(R.id.button)

        button.setOnClickListener {
            val intent = Intent(Settings.ACTION_DISPLAY_SETTINGS)

            val display = windowManager.currentWindowMetrics
            val oldDisplay = (getSystemService(WINDOW_SERVICE) as WindowManager).defaultDisplay
            val screenWidth: Int = display.bounds.width()
            val screenHeight: Int = display.bounds.height()
            val orientation = oldDisplay.orientation

            val info = "Width: $screenWidth; Height: $screenHeight; Orientation: $orientation";
            Log.i("Screen", info)

            var screen = ""
            val metrics = DisplayMetrics()

            if (Build.VERSION.SDK_INT >= 30) {
                oldDisplay.apply {
                    getRealMetrics(metrics)
                    screen = """
                        Width: ${metrics.widthPixels} pixels
                        Height: ${metrics.heightPixels} pixels 
                        The Logical Density: ${metrics.density}  
                        X Dimension: ${metrics.xdpi} dot/inch
                        Y Dimension: ${metrics.ydpi} dot/inch
                        The screen density expressed as dots-per-inch: ${metrics.densityDpi}
                        A scaling factor for fonts displayed on the display: ${metrics.scaledDensity}
                    """.trimIndent()
                }
            }

            print(screen)
            getDeviceDensity()

            try {
                val curBrightnessValue = Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS)
                Log.i("Screen", "Current screen brightness: $curBrightnessValue")
            } catch (e: SettingNotFoundException) {
                e.printStackTrace()
            }
            /*if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            }*/
        }

        val seekBar: SeekBar = findViewById(R.id.seekBar)
        val textView: TextView = findViewById(R.id.textView)

        val brightness = Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS)
        seekBar.progress = brightness

        textView.text = "Screen Brightness: $brightness"

        val canWrite = Settings.System.canWrite(this)

        if (!canWrite) {
            seekBar.isEnabled = false
            allowWritePermission()
        }

        seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                textView.text = "Screen Brightness: $progress"
                setBrightness(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun getDeviceDensity() {
        val dpiDensity: Int = resources.displayMetrics.densityDpi

        when (dpiDensity) {
            DisplayMetrics.DENSITY_LOW -> Log.i("Density", "Density_low")
            DisplayMetrics.DENSITY_MEDIUM -> Log.i("Density", "Density_medium")
            DisplayMetrics.DENSITY_HIGH -> Log.i("Density", "Density_high")
            DisplayMetrics.DENSITY_XHIGH -> Log.i("Density", "Density_xhigh")
            DisplayMetrics.DENSITY_XXHIGH -> Log.i("Density", "Density_xxhigh")
            DisplayMetrics.DENSITY_XXXHIGH -> Log.i("Density", "Density_xxxhigh")
        }
    }

    private fun allowWritePermission() {
        val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS, Uri.parse("package:$packageName"))
        startActivity(intent)
    }

    fun setBrightness(value: Int) {
        Settings.System.putInt(
            this.contentResolver,
            Settings.System.SCREEN_BRIGHTNESS,
            value
        )
        /*val backLightValue: Float = (value / 100).toFloat()
        val textView: TextView = findViewById(R.id.textView)
        textView.text = backLightValue.toString()

        val layoutParams: WindowManager.LayoutParams = window.attributes
        layoutParams.screenBrightness= Settings.System.getInt(contentResolver,
            Settings.System.SCREEN_BRIGHTNESS).toFloat()

        window.attributes = layoutParams*/
    }
}