package com.example.myapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class home_page : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_page)

        var imageButton6 = findViewById<ImageButton>(R.id.imageButton6)
        imageButton6.setOnClickListener{
            val intent = Intent(this, dashboard::class.java)
            startActivity(intent)

        }
    }
}
