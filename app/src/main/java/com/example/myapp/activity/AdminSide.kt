package com.example.myapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.myapp.R

class AdminSide : AppCompatActivity() {

    private lateinit var viewbtn1: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_side)
        //directing to recycleview of Applied users
        var viewbtn1 = findViewById<Button>(R.id.viewbtn1)
        viewbtn1.setOnClickListener {
            val intent = Intent(this, Fetching::class.java)
            startActivity(intent)

        }
    }
}