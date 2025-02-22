package com.manual

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import kotlin.math.log

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail)

        val webView: WebView = findViewById(R.id.webView)
        val intent = intent
        val resName = "n" + intent.getIntExtra("title", 0)
        val content: Context = baseContext

        val text = readRawTextFile(content, resources.getIdentifier(resName, "raw",
            "com.manual"))

        webView.loadDataWithBaseURL(null, text, "text/html", "ru_RU", null)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun readRawTextFile(context: Context, resId: Int): String {
        val inputStream: InputStream = context.resources.openRawResource(resId)
        val buffReader = BufferedReader(InputStreamReader(inputStream))
        var line: String?
        val builder = StringBuilder()

        try {
            while (buffReader.readLine().also { line = it } != null) {
                builder.append(line)
                builder.append("<br/>")
            }
        } catch (e: IOException) {
            return e.localizedMessage
        }

        return builder.toString()
    }
}