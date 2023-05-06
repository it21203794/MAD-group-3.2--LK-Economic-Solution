package com.example.myapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView

class find_selected_project : AppCompatActivity() {

    private lateinit var projectId: TextView
    private lateinit var projectTitle: TextView
    private lateinit var projectBudget: TextView
    private lateinit var projectAbout: TextView
    private lateinit var btnUpdate: ImageButton
    private lateinit var btnDelete: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.find_selected_project)

        initView()
        setValuesToViews()

    }

    private fun initView(){
        projectId = findViewById(R.id.projectId)
        projectTitle = findViewById(R.id.projectTitle)
        projectBudget = findViewById(R.id.projectBudget)
        projectAbout = findViewById(R.id.projectAbout)


    }
    private fun setValuesToViews(){
        projectId.text = intent.getStringExtra("projectId")
        projectTitle.text = intent.getStringExtra("ptitle")
        projectBudget.text = intent.getStringExtra("pbudget")
        projectAbout.text = intent.getStringExtra("pabout")
    }
}