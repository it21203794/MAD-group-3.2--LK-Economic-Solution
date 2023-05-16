package com.example.diwya2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Addhospital : AppCompatActivity() {
  //database variable creator

    private lateinit var dbRef: DatabaseReference

    private lateinit var editName: EditText
    //private lateinit var editAddress: EditText
    private lateinit var editCity: EditText
    private lateinit var editEmail: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addhospital)

        val btnSubmit = findViewById<Button>(R.id.btnsubmit)

        btnSubmit.setOnClickListener {
            val intent = Intent( this,UpdateRecords::class.java)
            startActivity(intent)
        }

        dbRef = FirebaseDatabase.getInstance().getReference("hospital")

        editName=findViewById<EditText>(R.id.editName)
        //editcusPhone=findViewById<EditText>(R.id.editAddress)
        editCity=findViewById<EditText>(R.id.editCity)
        editEmail=findViewById<EditText>(R.id.editEmail)


        btnSubmit.setOnClickListener {
            saveHosData()
        }


    }

    private fun saveHosData() {
        val HosName = editName.text.toString()

        val HosCity = editCity.text.toString()
        val HosEmail = editEmail.text.toString()


        val HosAddress = dbRef.push().key!!

        val user = HospitalModel(HosName, HosAddress, HosCity, HosEmail)

        dbRef.child(HosAddress).setValue(user)


            .addOnCanceledListener {
                Toast.makeText(this, "sucsesfull ", Toast.LENGTH_SHORT).show()

                editName.setText("")
                //editAddress.setText("")
                editEmail.setText("")
                editCity.setText("")
                //ditcusNight.setText("")
                //editcusPeople.setText("")

            }
            .addOnFailureListener { err ->
                Toast.makeText(this, "Error  ${err.message})", Toast.LENGTH_SHORT).show()
            }

        val submit=findViewById<Button>(R.id.btnsubmit)

        submit.setOnClickListener {
            val intent = Intent( this,UpdateRecords::class.java)
            startActivity(intent)
        }
    }
}