package com.example.myapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.example.myapp.models.Gig
import com.example.myapp.models.Project
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class create_new_project : AppCompatActivity() {

    private lateinit var ptitle : EditText
    private lateinit var pbudget : EditText
    private lateinit var pabout : EditText
    private lateinit var btnCreate : ImageButton

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_new_project)

        ptitle = findViewById(R.id.ptitle)
        pbudget = findViewById(R.id.pbudget)
        pabout = findViewById(R.id.pabout)
        btnCreate = findViewById(R.id.btnCreate)

        dbRef = FirebaseDatabase.getInstance().getReference("Project")

        btnCreate.setOnClickListener {
            saveProjectData()
        }
    }
        private fun saveProjectData(){
            val qpTitle = ptitle.text.toString()
            val qpBudget = pbudget.text.toString()
            val qpAbout = pabout.text.toString()

            if(qpTitle.isEmpty()){
                ptitle.error = "Please enter category"
            }
            if(qpBudget.isEmpty()){
                pbudget.error = "Please enter category"
            }
            if(qpAbout.isEmpty()){
                pabout.error = "Please enter category"
            }

            val projectId = dbRef.push().key!!

            val project = Project(projectId,qpTitle,qpBudget,qpAbout)

            dbRef.child(projectId).setValue(project)
                .addOnCompleteListener{
                    Toast.makeText(this,"insert data successfully", Toast.LENGTH_LONG).show()
                    ptitle.text.clear()
                    pbudget.text.clear()
                    pabout.text.clear()
                }.addOnFailureListener{err ->
                    Toast.makeText(this,"Error ${err.message}", Toast.LENGTH_LONG).show()
                }



        }
    }
