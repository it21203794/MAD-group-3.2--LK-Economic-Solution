package com.example.myapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import com.example.myapp.R

class Main_home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_home)

        var imageButton7 = findViewById<ImageButton>(R.id.imageButton7)
        imageButton7.setOnClickListener{
            val intent = Intent(this, Jobwelcome::class.java)
            startActivity(intent)
        }

    }
}