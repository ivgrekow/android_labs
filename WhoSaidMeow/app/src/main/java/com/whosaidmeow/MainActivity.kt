package com.whosaidmeow

import android.content.pm.ActivityInfo
import android.content.res.AssetFileDescriptor
import android.content.res.AssetManager
import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Im
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private lateinit var soundPool: SoundPool
    private lateinit var assetManager: AssetManager

    private var cockSound: Int = 0
    private var lionSound: Int = 0
    private var horseSound: Int = 0
    private var dogSound: Int = 0
    private var toucanSound: Int = 0
    private var sheepSound: Int = 0

    private var streamId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        val cockImage: ImageView = findViewById(R.id.image_cock)
        val lionImage: ImageView = findViewById(R.id.image_lion)
        val horseImage: ImageView = findViewById(R.id.image_horse)
        val dogImage: ImageView = findViewById(R.id.image_dog)
        val toucanImage: ImageView = findViewById(R.id.image_toucan)
        val sheepImage: ImageView = findViewById(R.id.image_sheep)

        val attributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        soundPool = SoundPool.Builder()
            .setAudioAttributes(attributes)
            .build()

        assetManager = assets
        cockSound = loadSound("cock.mp3")
        lionSound = loadSound("lion.mp3")
        horseSound = loadSound("horse.mp3")
        dogSound = loadSound("dog.mp3")
        toucanSound = loadSound("toucan.mp3")
        sheepSound = loadSound("sheep.mp3")

        cockImage.setOnClickListener {
            playSound(cockSound)
        }

        lionImage.setOnClickListener {
            playSound(lionSound)
        }

        horseImage.setOnClickListener {
            playSound(horseSound)
        }

        dogImage.setOnClickListener {
            playSound(dogSound)
        }

        toucanImage.setOnClickListener {
            playSound(toucanSound)
        }

        sheepImage.setOnTouchListener { view, motionEvent ->
            val eventAction: Int = motionEvent.action

            if (eventAction == MotionEvent.ACTION_UP) {
                if (streamId > 0) {
                    soundPool.stop(streamId)
                }
            }

            if (eventAction == MotionEvent.ACTION_DOWN) {
                streamId = playSound(sheepSound)
            }

            if (motionEvent.action == MotionEvent.ACTION_CANCEL) {
                soundPool.stop(streamId)
            }

            true
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onPause() {
        super.onPause()
        soundPool.release()
    }

    private fun playSound(sound: Int): Int {
        if (sound > 0) {
            streamId = soundPool.play(sound, 1F, 1F, 1, 0, 1F)
        }

        return streamId
    }

    private fun loadSound(fileName: String): Int {
        val afd: AssetFileDescriptor = try {
            application.assets.openFd(fileName)
        } catch (e: IOException) {
            e.printStackTrace()
            Log.d("Meow", "Не могу загрузить файл $fileName!")

            return -1
        }

        return soundPool.load(afd, 1)
    }
}