package com.example.myapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class find_project : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.find_project)

        var button4 = findViewById<Button>(R.id.button4)
        button4.setOnClickListener{
            val intent = Intent(this, view_details::class.java)
            startActivity(intent)

        }

    }



}