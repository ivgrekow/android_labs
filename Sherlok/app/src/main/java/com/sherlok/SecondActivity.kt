package com.sherlok

import android.content.Intent
import android.os.Bundle
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity

const val THIEF = "com.sherlock"

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val radioGroup: RadioGroup = findViewById(R.id.radio_group)

        radioGroup.setOnCheckedChangeListener {
            _, optionId ->
            val answerIntent = Intent()
            when (optionId) {
                R.id.radio_dog -> answerIntent.putExtra(THIEF, "Пёс")
                R.id.radio_crow -> answerIntent.putExtra(THIEF, "Ворона")
                R.id.radio_cat -> answerIntent.putExtra(THIEF, "Лошадь")
            }

            setResult(RESULT_OK, answerIntent)
            finish()
        }
    }
}