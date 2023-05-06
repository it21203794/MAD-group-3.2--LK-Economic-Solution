package com.example.myapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.myapp.R

class Jobwelcome : AppCompatActivity() {
    private lateinit var fetchjobdata:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jobwelcome)


        var fetchjobdata = findViewById<Button>(R.id.fetchjobdata)
        fetchjobdata.setOnClickListener {
            val intent = Intent(this, applying::class.java)
            startActivity(intent)
        }
    }
}