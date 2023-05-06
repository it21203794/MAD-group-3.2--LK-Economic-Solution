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

    private lateinit var gigId: TextView
    private lateinit var gigCategory: TextView
    private lateinit var gigMainSkill: TextView
    private lateinit var btnUpdate: ImageButton
    private lateinit var btnDelete: ImageButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.gig_view)

        initView()
        setValuesToViews()

        btnUpdate.setOnClickListener{
            openUpdateDialog(
                intent.getStringExtra("gigId").toString(),
                intent.getStringExtra("category").toString()
            )
        }

        btnDelete.setOnClickListener{
            deleteRecord(
                intent.getStringExtra("gigId").toString()
            )
        }
    }
    private fun initView(){
        gigId = findViewById(R.id.gigId)
        gigCategory = findViewById(R.id.gigCategory)
        gigMainSkill = findViewById(R.id.gigMainSkill)

        btnDelete = findViewById(R.id.btnDelete)
        btnUpdate = findViewById(R.id.btnUpdate)
    }
    private fun setValuesToViews(){
        gigId.text = intent.getStringExtra("gigId")
        gigCategory.text = intent.getStringExtra("category")
        gigMainSkill.text = intent.getStringExtra("mainSkill")
    }





    private fun deleteRecord(Id:String){
        val dbRef = FirebaseDatabase.getInstance().getReference("Gig").child(Id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this,"Gig data deleted", Toast.LENGTH_LONG).show()
            val intent = Intent(this, become_a_freelancer::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{error ->
            Toast.makeText(this,"Deleting Err ${error.message}", Toast.LENGTH_LONG).show()

        }
    }



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

        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        etCategory.setText( intent.getStringExtra("category").toString())
        etMainSkill.setText( intent.getStringExtra("mainSkill").toString())

        mDialog.setTitle("Updating $category Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener{
            updateGigData(
                gigId,
                etCategory.text.toString(),
                etMainSkill.text.toString()
            )
            Toast.makeText(applicationContext,"Gig Data Updated", Toast.LENGTH_LONG).show()

            gigCategory.text = etCategory.text.toString()
            gigMainSkill.text = etMainSkill.text.toString()

            alertDialog.dismiss()
        }

    }

    private fun updateGigData(
        id:String,
        category: String,
        mainSkill: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Gig").child(id)
        val gigInfo = Gig(id,category,mainSkill)
        dbRef.setValue(gigInfo)
    }

}