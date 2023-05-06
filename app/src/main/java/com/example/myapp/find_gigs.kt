package com.example.myapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView

class find_gigs : AppCompatActivity() {

    private lateinit var gigId: TextView
    private lateinit var gigCategory: TextView
    private lateinit var gigMainSkill: TextView
    private lateinit var btnUpdate: ImageButton
    private lateinit var btnDelete: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.find_gigs)

        initView()
        setValuesToViews()
    }

    private fun initView(){
        gigId = findViewById(R.id.gigId)
        gigCategory = findViewById(R.id.gigCategory)
        gigMainSkill = findViewById(R.id.gigMainSkill)


    }
    private fun setValuesToViews(){
        gigId.text = intent.getStringExtra("gigId")
        gigCategory.text = intent.getStringExtra("category")
        gigMainSkill.text = intent.getStringExtra("mainSkill")
    }
}