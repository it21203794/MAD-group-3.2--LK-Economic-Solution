package com.example.diwya2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class KDU : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kdu)

        val Addnew=findViewById<Button>(R.id.btnAddnew)

        Addnew.setOnClickListener {
            val intent = Intent( this,Addhospital::class.java)
            startActivity(intent)
        }


    }
}