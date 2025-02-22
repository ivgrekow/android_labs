package com.manual

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private val titles = arrayOf(
        "00: Начало",
        "01: Чем кормить кота?",
        "02: Как гладить кота",
        "03: Как спит кот",
        "04: Как играть с котом",
        "05: Как разговаривать с котом",
        "06: Интересные факты из жизни котов",
        "07: Как назвать кота"
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val listView: ListView = findViewById(R.id.listView)
        listView.adapter = ArrayAdapter<Any?>(this, android.R.layout.simple_list_item_1, titles)
        listView.isTextFilterEnabled = true

        listView.onItemClickListener =
            OnItemClickListener { a, v, position, id ->
                val intent = Intent()
                intent.setClass(this@MainActivity, DetailActivity::class.java)
                intent.putExtra("title", position)

                startActivity(intent)
            }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}