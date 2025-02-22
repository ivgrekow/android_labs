package com.sunrise

import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val sunImageView: ImageView = findViewById(R.id.sun)
        val sunRiseAnimation: Animation = AnimationUtils.loadAnimation(this, R.anim.sun_rise)

        val clockImageView: ImageView = findViewById(R.id.clock)
        val clockTurnAnimation: Animation = AnimationUtils.loadAnimation(this, R.anim.clock_turn)

        val hourImageView: ImageView = findViewById(R.id.hour_hand)
        val hourTurnAnimation: Animation = AnimationUtils.loadAnimation(this, R.anim.hour_turn)

        sunImageView.startAnimation(sunRiseAnimation)
        clockImageView.startAnimation(clockTurnAnimation)
        hourImageView.startAnimation(hourTurnAnimation)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}