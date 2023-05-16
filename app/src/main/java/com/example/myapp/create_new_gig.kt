package com.example.myapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.example.myapp.models.Gig
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class create_new_gig : AppCompatActivity() {

    // Declare the EditText fields and ImageButton
    private lateinit var category : EditText
    private lateinit var mainSkill : EditText
    private lateinit var about12 : EditText

    private lateinit var btnCreate : ImageButton

    // Declare the database reference
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_new_gig)

        // Initialize the EditText fields and ImageButton
        category = findViewById(R.id.category)
        mainSkill = findViewById(R.id.mainSkill)
        about12 = findViewById(R.id.about12)
        btnCreate = findViewById(R.id.btnCreate)

        // Get the Firebase database reference for the "Gig" node
        dbRef = FirebaseDatabase.getInstance().getReference("Gig")

        // Set a click listener for the create button
        btnCreate.setOnClickListener {
            saveGigData()
        }

    }
    // Function to save gig data to Firebase database
    private fun saveGigData(){
        // Get the text values from the EditText fields
        val qCategory = category.text.toString()
        val qMainSkill = mainSkill.text.toString()
        val qAbout12 = about12.text.toString()

        // Validate the input fields and show errors if empty
        if(qCategory.isEmpty()){
            category.error = "Please enter category"
        }
        if(qMainSkill.isEmpty()){
            mainSkill.error = "Please enter mainSkill"
        }
        if(qAbout12.isEmpty()){
            about12.error = "Please enter about"
        }

        // Generate a unique gigId using push().key!!
        val gigId = dbRef.push().key!!
        // Create a Gig object with the entered data

        val gig = Gig(gigId,qCategory,qMainSkill,qAbout12)

        // Save the gig data to the database
        dbRef.child(gigId).setValue(gig)
            .addOnCompleteListener{
                Toast.makeText(this,"insert data successfully", Toast.LENGTH_LONG).show()
                // Clear the EditText fields after successful data insertion
                category.text.clear()
                mainSkill.text.clear()
                about12.text.clear()
            }.addOnFailureListener{err ->
                Toast.makeText(this,"Error ${err.message}",Toast.LENGTH_LONG).show()
            }


        // Set a click listener for the ImageButton
        var imageView41 = findViewById<ImageButton>(R.id.imageView41)
        imageView41.setOnClickListener{
            // Start the find_freelancers activity
            val intent = Intent(this, find_freelancers::class.java)
            startActivity(intent)

        }
    }
}