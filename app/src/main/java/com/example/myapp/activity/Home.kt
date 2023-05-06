package com.example.myapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.myapp.R

class Home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        var button12 = findViewById<Button>(R.id.button12)
        button12.setOnClickListener{
            val intent = Intent(this, Jobwelcome::class.java)
            startActivity(intent)
        }
    }
}