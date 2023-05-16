package com.example.diwya2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val view1=findViewById<Button>(R.id.btnview1)


        view1.setOnClickListener {
            val intent = Intent( this,KDU::class.java)
            startActivity(intent)
        }
    }
}