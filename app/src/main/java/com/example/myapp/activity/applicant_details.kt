package com.example.myapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.myapp.R
import com.example.myapp.models.applicantModel
import com.google.firebase.database.FirebaseDatabase

class applicant_details : AppCompatActivity() {

    //creating variables for TextViews
    private lateinit var tvAplId:TextView
    private lateinit var tvAplName:TextView
    private lateinit var tvAplEmail:TextView
    private lateinit var tvAplphone:TextView
    private lateinit var tvAplEdu:TextView
    private lateinit var btnUpdate:Button
    private lateinit var btnDelete:Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_applicant_details)
        //function calling
        initView()
        setValuesToViews()

        btnUpdate.setOnClickListener{
            openUpdateDialog(
                intent.getStringExtra("aplID").toString(),
                intent.getStringExtra("aplName").toString(),

            )
        }

        btnDelete.setOnClickListener{
            deleteRecord(
                intent.getStringExtra("aplID").toString()
            )
        }
    }

    private fun deleteRecord(
        id: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("applicant").child(id)
        val mTask =dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this,"Applicant Data Deleted",Toast.LENGTH_LONG).show()
            val intent = Intent(this,Fetching::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error ->
            Toast.makeText(this,"Deleting Err ${error.message}",Toast.LENGTH_LONG).show()
        }

    }



    private fun initView(){
        tvAplId = findViewById(R.id.tvAplId)
        tvAplName = findViewById(R.id.tvAplName)
        tvAplEmail = findViewById(R.id.tvAplEmail)
        tvAplphone = findViewById(R.id.tvAplphone)
        tvAplEdu = findViewById(R.id.tvAplEdu)

        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }

    private fun setValuesToViews(){
        tvAplId.text = intent.getStringExtra("aplID")
        tvAplName.text =intent.getStringExtra("aplName")
        tvAplEmail.text =intent.getStringExtra("aplEmail")
        tvAplphone.text =intent.getStringExtra("aplPhone")
        tvAplEdu.text =intent.getStringExtra("aplEdu")
    }

    private fun openUpdateDialog(
        aplId:String,
        aplName:String

    ){
        val mDialog = AlertDialog.Builder(this)
        val inflater =layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_dialog,null)

        mDialog.setView(mDialogView)

        val etAplName = mDialogView.findViewById<EditText>(R.id.etAptName)
        val etAplEmail = mDialogView.findViewById<EditText>(R.id.etAptEmail)
        val etAplPhone = mDialogView.findViewById<EditText>(R.id.etAptPhone)
        val etAplEdu = mDialogView.findViewById<EditText>(R.id.etAptEdu)
        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        etAplName.setText(intent.getStringExtra("aplName").toString())
        etAplEmail.setText(intent.getStringExtra("aplEmail").toString())
        etAplPhone.setText(intent.getStringExtra("aplPhone").toString())
        etAplEdu.setText(intent.getStringExtra("aplEdu").toString())

        mDialog.setTitle("Updating $aplName Record")

        val alertDialog =mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener{
            updateaplData(
                aplId,
                etAplName.text.toString(),
                etAplEmail.text.toString(),
                etAplPhone.text.toString(),
                etAplEdu.text.toString()
            )

            Toast.makeText(applicationContext,"Applicant Data Updated",Toast.LENGTH_LONG).show()


            tvAplName.text = etAplName.text.toString()
            tvAplEmail.text =etAplEmail.text.toString()
            tvAplphone.text =etAplPhone.text.toString()
            tvAplEdu.text =etAplEdu.text.toString()

            alertDialog.dismiss()

        }

    }

    private fun updateaplData(
        id:String,
        name:String,
        email:String,
        phone:String,
        edu:String
    ){
        val dbRef =FirebaseDatabase.getInstance().getReference("applicant").child(id)
        val aplInfo = applicantModel(id,name,email,phone,edu)
        dbRef.setValue(aplInfo)

    }

}