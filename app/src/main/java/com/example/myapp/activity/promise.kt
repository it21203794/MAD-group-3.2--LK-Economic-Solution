package com.example.myapp.activity


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.myapp.R

class promise : AppCompatActivity() {
    private lateinit var viewbtn:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_promise)

         var viewbtn = findViewById<Button>(R.id.viewbtn)
        viewbtn.setOnClickListener {
            val intent = Intent(this, Fetching::class.java)
            startActivity(intent)
        }

    }
}