package com.keyboard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.text.InputType
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodSubtype
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Locale

class MainActivity : AppCompatActivity(), OnEditorActionListener  {
    private var backPressed: Long = 0
    private lateinit var inputSearch: EditText
    private lateinit var inputGo: EditText
    private lateinit var inputSend: EditText
    private lateinit var inputNext: EditText
    private lateinit var inputDone: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        inputSearch = findViewById(R.id.inputSearch)
        //inputSearch.setImeActionLabel("Meow", EditorInfo.IME_ACTION_SEARCH)
        //inputSearch.imeOptions = EditorInfo.IME_ACTION_SEARCH
        inputGo = findViewById(R.id.inputGo)
        inputSend = findViewById(R.id.inputSend)
        inputNext = findViewById(R.id.inputNext)
        inputDone = findViewById(R.id.inputDone)

        inputSearch.setOnEditorActionListener(this)
        inputGo.setOnEditorActionListener(this)
        inputSend.setOnEditorActionListener(this)
        inputNext.setOnEditorActionListener(this)
        inputDone.setOnEditorActionListener(this)

        val view: ConstraintLayout = findViewById(R.id.main)

        view.setOnKeyListener { _, keyCode, event ->
            run {
                Log.i("MainActivity", keyCode.toString())
                if (event.action == KeyEvent.ACTION_DOWN) {
                    Toast.makeText(this, "Нажата вниз клавиша $keyCode", Toast.LENGTH_SHORT).show()
                    true
                } else {
                    Toast.makeText(this, "Отпущена клавиша $keyCode", Toast.LENGTH_SHORT).show()
                    true
                }
            }
            false
        }
        //inputSearch.inputType = InputType.TYPE_CLASS_DATETIME
        val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(inputSearch.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)

        val button: Button = findViewById(R.id.button)

        button.setOnClickListener {
            val im: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            val ims: InputMethodSubtype? = im.currentInputMethodSubtype
            val localeString = ims?.locale
            val locale = Locale(localeString)
            val currentLanguage = locale.displayLanguage
            Toast.makeText(applicationContext, currentLanguage, Toast.LENGTH_SHORT).show()
        }

        val intent1 = Intent(Settings.ACTION_INPUT_METHOD_SETTINGS)
        val intent2 = Intent(Settings.ACTION_HARD_KEYBOARD_SETTINGS)

        if (intent2.resolveActivity(packageManager) != null) {
            startActivity(intent2)
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        when (keyCode) {
            KeyEvent.KEYCODE_MENU ->
                Toast.makeText(this, "Нажата кнопка menu", Toast.LENGTH_SHORT).show()
            KeyEvent.KEYCODE_SEARCH ->
                Toast.makeText(this, "Нажата кнопка поиск", Toast.LENGTH_SHORT).show()
            KeyEvent.KEYCODE_VOLUME_UP -> event?.startTracking()
            KeyEvent.KEYCODE_VOLUME_DOWN -> {
                Toast.makeText(this, "Нажата кнопка громкости", Toast.LENGTH_SHORT).show()
                return false
            }
        }

        return super.onKeyDown(keyCode, event)
    }

    override fun onKeyLongPress(keyCode: Int, event: KeyEvent?): Boolean {
        val editText: EditText = findViewById(R.id.inputSearch)

        if (keyCode == KeyEvent.KEYCODE_MENU) {
            editText.setText("Long Press")
            return true
        }

        return super.onKeyLongPress(keyCode, event)
    }

    /*override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        Log.i("MainActivity", keyCode.toString())
        return true
    }*/

    override fun onBackPressed() {
        /*AlertDialog.Builder(this).apply {
            setTitle("Подтверждение")
            setMessage("Вы уверены, что хотите выйти из программы?")

            setPositiveButton("Да") { _, _ ->
                super.onBackPressed()
            }

            setNegativeButton("Нет") { _, _ ->
                Toast.makeText(this@MainActivity, "Спасибо!", Toast.LENGTH_LONG).show()
            }

            setCancelable(true)
        }.create().show()*/
        if (backPressed + 2000 > System.currentTimeMillis()) {
            super.onBackPressed()
        } else {
            Toast.makeText(baseContext, "Нажмите ещё один раз для выхода", Toast.LENGTH_SHORT).show()
            backPressed = System.currentTimeMillis()
        }
    }

    override fun onUserLeaveHint() {
        Toast.makeText(applicationContext, "Нажата кнопка Home", Toast.LENGTH_SHORT).show()
        super.onUserLeaveHint()
    }

    override fun onEditorAction(textView: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            if (inputSearch.text.toString() != "cat") {
                Toast.makeText(this, "Не буду ничего искать!", Toast.LENGTH_SHORT).show()
            }

            return true
        } else if (actionId == EditorInfo.IME_ACTION_GO) {
            Toast.makeText(this, "Вы нажали enter с actionGo", Toast.LENGTH_SHORT).show()
            return true
        } else if (actionId == EditorInfo.IME_ACTION_SEND) {
            Toast.makeText(this, "Вы нажали enter с actionSend", Toast.LENGTH_SHORT).show()
            return true
        } else if (actionId == EditorInfo.IME_ACTION_NEXT) {
            Toast.makeText(this, "Вы нажали enter с actionNext", Toast.LENGTH_SHORT).show()
            return true
        } else if (actionId == EditorInfo.IME_ACTION_DONE) {
            Toast.makeText(this, "Вы нажали enter с actionDone", Toast.LENGTH_SHORT).show()
            return true
        }

        return false
    }
}