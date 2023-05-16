package com.example.myapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView

class find_gigs : AppCompatActivity() {

    // Declare the necessary views
    private lateinit var gigId: TextView
    private lateinit var gigCategory: TextView
    private lateinit var gigMainSkill: TextView
    private lateinit var gigAbout12: TextView
    private lateinit var btnUpdate: ImageButton
    private lateinit var btnDelete: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.find_gigs)// Set the layout for this activity

        initView()// Initialize the views
        setValuesToViews()// Set the values to the views
    }

    private fun initView(){
        // Initialize the views by       finding their corresponding IDs
        gigId = findViewById(R.id.gigId)
        gigCategory = findViewById(R.id.gigCategory)
        gigMainSkill = findViewById(R.id.gigMainSkill)
        gigAbout12 = findViewById(R.id.gigAbout12)


    }
    private fun setValuesToViews(){
        // Set the values to the views based on the received intent extras
        gigId.text = intent.getStringExtra("gigId")
        gigCategory.text = intent.getStringExtra("category")
        gigMainSkill.text = intent.getStringExtra("mainSkill")
        gigAbout12.text = intent.getStringExtra("about12")
    }
}