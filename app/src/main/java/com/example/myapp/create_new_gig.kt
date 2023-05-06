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

    private lateinit var category : EditText
    private lateinit var mainSkill : EditText
    private lateinit var btnCreate : ImageButton

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_new_gig)

        category = findViewById(R.id.category)
        mainSkill = findViewById(R.id.mainSkill)
        btnCreate = findViewById(R.id.btnCreate)

        dbRef = FirebaseDatabase.getInstance().getReference("Gig")

        btnCreate.setOnClickListener {
            saveGigData()
        }

    }
    private fun saveGigData(){
        val qCategory = category.text.toString()
        val qMainSkill = mainSkill.text.toString()

        if(qCategory.isEmpty()){
            category.error = "Please enter category"
        }
        if(qMainSkill.isEmpty()){
            mainSkill.error = "Please enter category"
        }

        val gigId = dbRef.push().key!!

        val gig = Gig(gigId,qCategory,qMainSkill)

        dbRef.child(gigId).setValue(gig)
            .addOnCompleteListener{
                Toast.makeText(this,"insert data successfully", Toast.LENGTH_LONG).show()
                category.text.clear()
                mainSkill.text.clear()
            }.addOnFailureListener{err ->
                Toast.makeText(this,"Error ${err.message}",Toast.LENGTH_LONG).show()
            }



        var imageView41 = findViewById<ImageButton>(R.id.imageView41)
        imageView41.setOnClickListener{
            val intent = Intent(this, find_freelancers::class.java)
            startActivity(intent)

        }
    }
}