package com.example.myapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.myapp.models.Gig
import com.google.firebase.database.FirebaseDatabase

class gig_view : AppCompatActivity() {
    // Declare the required views and buttons

    private lateinit var gigId: TextView
    private lateinit var gigCategory: TextView
    private lateinit var gigMainSkill: TextView
    private lateinit var gigAbout12: TextView
    private lateinit var btnUpdate: ImageButton
    private lateinit var btnDelete: ImageButton



    // This method is called when the activity is created
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.gig_view)

        // Initialize the views and buttons
        initView()

        // Set the values to the views
        setValuesToViews()

        // Set a click listener for the update button
        btnUpdate.setOnClickListener{
            // Open the update dialog with the gig details as parameters
            openUpdateDialog(
                intent.getStringExtra("gigId").toString(),
                intent.getStringExtra("category").toString()

            )
        }
        // Set a click listener for the delete button

        btnDelete.setOnClickListener{

            // Delete the gig record from the Firebase database
            deleteRecord(
                intent.getStringExtra("gigId").toString()
            )
        }
    }
    // Initialize the views and buttons by finding them in the layout XML
    private fun initView(){
        gigId = findViewById(R.id.gigId)
        gigCategory = findViewById(R.id.gigCategory)
        gigMainSkill = findViewById(R.id.gigMainSkill)
        gigAbout12 = findViewById(R.id.gigAbout12)

        btnDelete = findViewById(R.id.btnDelete)
        btnUpdate = findViewById(R.id.btnUpdate)
    }

    // Set the values to the views based on the intent extras received from the previous screen
    private fun setValuesToViews(){
        gigId.text = intent.getStringExtra("gigId")
        gigCategory.text = intent.getStringExtra("category")
        gigMainSkill.text = intent.getStringExtra("mainSkill")
        gigAbout12.text = intent.getStringExtra("about12")
    }




    // Delete the gig record from the Firebase database

    private fun deleteRecord(Id:String){
        val dbRef = FirebaseDatabase.getInstance().getReference("Gig").child(Id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this,"Gig data deleted", Toast.LENGTH_LONG).show()
            // After successful deletion, navigate to the become_a_freelancer activity
            val intent = Intent(this, become_a_freelancer::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{error ->
            Toast.makeText(this,"Deleting Err ${error.message}", Toast.LENGTH_LONG).show()

        }
    }


    // Open the update dialog to allow the user to modify the gig details
    private fun openUpdateDialog(
        gigId: String,
        category: String

    ){
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_dialog,null)

        mDialog.setView(mDialogView)

        val etCategory = mDialogView.findViewById<EditText>(R.id.etCategory)
        val etMainSkill = mDialogView.findViewById<EditText>(R.id.etMainSkill)
        val etAbout12 = mDialogView.findViewById<EditText>(R.id.etAbout12)

        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        etCategory.setText( intent.getStringExtra("category").toString())
        etMainSkill.setText( intent.getStringExtra("mainSkill").toString())
        etAbout12.setText( intent.getStringExtra("about12").toString())

        mDialog.setTitle("Updating $category Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener{
            // Update gig data with the entered values
            updateGigData(
                gigId,
                etCategory.text.toString(),
                etMainSkill.text.toString(),
                etAbout12.text.toString()
            )
            // Show a toast message indicating that the gig data has been updated
            Toast.makeText(applicationContext,"Gig Data Updated", Toast.LENGTH_LONG).show()

            // Update the displayed gig information with the entered values
            gigCategory.text = etCategory.text.toString()
            gigMainSkill.text = etMainSkill.text.toString()
            gigAbout12.text = etAbout12.text.toString()

            alertDialog.dismiss()// Dismiss the dialog
        }

    }




    private fun updateGigData(
        id:String,
        category: String,
        mainSkill: String,
        about12: String
    ){
        // Get the reference to the "Gig" node in the Firebase Realtime Database
        val dbRef = FirebaseDatabase.getInstance().getReference("Gig").child(id)
        // Create a Gig object with the provided values
        val gigInfo = Gig(id,category,mainSkill,about12)
        // Set the value of the "Gig" node to the gigInfo object
        dbRef.setValue(gigInfo)
    }

}