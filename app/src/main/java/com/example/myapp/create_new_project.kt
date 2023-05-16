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

    // Declare the necessary views and database reference variables
    private lateinit var ptitle : EditText
    private lateinit var pbudget : EditText
    private lateinit var pabout : EditText
    private lateinit var btnCreate : ImageButton

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_new_project)

        // Initialize the views by finding them in the layout
        ptitle = findViewById(R.id.ptitle)
        pbudget = findViewById(R.id.pbudget)
        pabout = findViewById(R.id.pabout)
        btnCreate = findViewById(R.id.btnCreate)

        // Get a reference to the "Project" node in the Firebase Realtime Database
        dbRef = FirebaseDatabase.getInstance().getReference("Project")

        // Set a click listener on the "Create" button
        btnCreate.setOnClickListener {
            saveProjectData()// Call a function to save the project data
        }
    }
    // Function to save the project data to the Firebase Realtime Database
        private fun saveProjectData(){
        // Get the values entered by the user
            val qpTitle = ptitle.text.toString()
            val qpBudget = pbudget.text.toString()
            val qpAbout = pabout.text.toString()

        // Validate the input fields
            if(qpTitle.isEmpty()){
                ptitle.error = "Please enter category"
            }
            if(qpBudget.isEmpty()){
                pbudget.error = "Please enter category"
            }
            if(qpAbout.isEmpty()){
                pabout.error = "Please enter category"
            }

        // Generate a unique key for the project using push().key
            val projectId = dbRef.push().key!!

        // Create a Project object with the entered data
            val project = Project(projectId,qpTitle,qpBudget,qpAbout)

        // Save the project data to the database
            dbRef.child(projectId).setValue(project)
                .addOnCompleteListener{
                    // Show a success message to the user and clear the input fields
                    Toast.makeText(this,"insert data successfully", Toast.LENGTH_LONG).show()
                    ptitle.text.clear()
                    pbudget.text.clear()
                    pabout.text.clear()
                }.addOnFailureListener{err ->
                    // Show an error message if the data couldn't be saved
                    Toast.makeText(this,"Error ${err.message}", Toast.LENGTH_LONG).show()
                }



        }
    }
