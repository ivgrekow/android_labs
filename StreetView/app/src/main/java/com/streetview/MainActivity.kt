package com.streetview

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val buttonMap: Button = findViewById(R.id.button_map)
        val buttonStreetView: Button = findViewById(R.id.button_street_view)
        val lat = 55.869555
        val lng = 37.503964

        buttonMap.setOnClickListener {
            //val geoUriString = String.format("geo:%s,%s?z=15", lat.toString(), lng.toString())
            val geoUriString = "geo:0,0?q=замбия&z=2"
            val geoUri = Uri.parse(geoUriString)
            val mapIntent = Intent(Intent.ACTION_VIEW, geoUri)

            startActivity(mapIntent)
        }

        buttonStreetView.setOnClickListener {
            val geoUriString =
                String.format("google.streetview:cbll=%s,%s&cbp=1,99.56,,1,2.0&mz=19", lat.toString(), lng.toString())
            val geoUri = Uri.parse(geoUriString)
            val mapIntent = Intent(Intent.ACTION_VIEW, geoUri)

            startActivity(mapIntent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}