package com.example.myapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.example.myapp.R
import com.example.myapp.models.applicantModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class applying : AppCompatActivity() {

    //creating variables for EditText

    private lateinit var fname:EditText
    private lateinit var email:EditText
    private lateinit var phone:EditText
    private lateinit var education:EditText
    private lateinit var button3:Button
    private lateinit var imageButton4:ImageButton

    private lateinit var dbRef:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_applying)
        //back Button

        var imageButton4 = findViewById<ImageButton>(R.id.imageButton4)
        imageButton4.setOnClickListener {
            val intent = Intent(this, Jobwelcome::class.java)
            startActivity(intent)
        }


        //getting userInput Values by ID
        fname = findViewById(R.id.fname)
        email = findViewById(R.id.email)
        phone = findViewById(R.id.phone)
        education = findViewById(R.id.education)
        button3 = findViewById(R.id.button3)

        //fireBase connection
        dbRef = FirebaseDatabase.getInstance().getReference("applicant")

        button3.setOnClickListener{
            saveapplicantData()
        }
    }

    private fun saveapplicantData(){

        //Assigning String values to variables
        val applicant_name = fname.text.toString()
        val  applicant_email =email.text.toString()
        val  applicant_phone = phone.text.toString()
        val  applicant_education = education.text.toString()

        //Input Validation
        if (applicant_name.isEmpty()){
            fname.error = "Please enter the name"
        }

        if (applicant_email.isEmpty()){
            email.error = "Please enter email"

        }

        if (applicant_phone.isEmpty()){
            phone.error = "Please enter phone number"
        }

        if (applicant_education.isEmpty()){
            education.error = "Please enter your education level"
        }



        //Creading a ID to Dataset
        val applicantId = dbRef.push().key!!

        val applicant = applicantModel(applicantId,applicant_name,applicant_email,applicant_phone,applicant_education)

        dbRef.child(applicantId).setValue(applicant)
            .addOnCompleteListener{

                Toast.makeText(this, "Data inserted successfully",Toast.LENGTH_LONG).show()
                val intent = Intent(this, promise::class.java)
                startActivity(intent)
                //cleaning the previous Values
                fname.text.clear()
                email.text.clear()
                phone.text.clear()
                education.text.clear()

            }.addOnFailureListener{ err ->
                Toast.makeText(this,"Error ${err.message}",Toast.LENGTH_LONG).show()
            }
    }

}