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
import com.example.myapp.models.Project
import com.google.firebase.database.FirebaseDatabase

class project_view : AppCompatActivity() {

    private lateinit var projectId: TextView
    private lateinit var projectTitle: TextView
    private lateinit var projectBudget: TextView
    private lateinit var projectAbout: TextView
    private lateinit var btnUpdate: ImageButton
    private lateinit var btnDelete: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.project_view)

        initView()
        setValuesToViews()

        btnUpdate.setOnClickListener{
            openUpdateDialog(
                intent.getStringExtra("projectId").toString(),
                intent.getStringExtra("ptitle").toString()
            )
        }

        btnDelete.setOnClickListener{
            deleteRecord(
                intent.getStringExtra("projectId").toString()
            )
        }
    }
    private fun initView(){
        projectId = findViewById(R.id.projectId)
        projectTitle = findViewById(R.id.projectTitle)
        projectBudget = findViewById(R.id.projectBudget)
        projectAbout = findViewById(R.id.projectAbout)

        btnDelete = findViewById(R.id.btnDelete)
        btnUpdate = findViewById(R.id.btnUpdate)
    }
    private fun setValuesToViews(){
        projectId.text = intent.getStringExtra("projectId")
        projectTitle.text = intent.getStringExtra("ptitle")
        projectBudget.text = intent.getStringExtra("pbudget")
        projectAbout.text = intent.getStringExtra("pabout")
    }

    private fun deleteRecord(Id:String){
        val dbRef = FirebaseDatabase.getInstance().getReference("Project").child(Id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this,"Project data deleted", Toast.LENGTH_LONG).show()
            val intent = Intent(this, post_a_project::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{error ->
            Toast.makeText(this,"Deleting Err ${error.message}", Toast.LENGTH_LONG).show()

        }
    }

    private fun openUpdateDialog(
        projectId: String,
        ptitle: String
        // Open a dialog to update project details

    ){
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.p_update_dialog,null)

        mDialog.setView(mDialogView)

        val etTitle = mDialogView.findViewById<EditText>(R.id.etTitle)
        val etBudget = mDialogView.findViewById<EditText>(R.id.etBudget)
        val etAbout = mDialogView.findViewById<EditText>(R.id.etAbout)

        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        etTitle.setText( intent.getStringExtra("ptitle").toString())
        etBudget.setText( intent.getStringExtra("pbudget").toString())
        etAbout.setText( intent.getStringExtra("pabout").toString())

        mDialog.setTitle("Updating $ptitle Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener{
            updateProjectData(
                projectId,
                etTitle.text.toString(),
                etBudget.text.toString(),
                etAbout.text.toString()
            )
            Toast.makeText(applicationContext,"Project Data Updated", Toast.LENGTH_LONG).show()

            projectTitle.text = etTitle.text.toString()
            projectBudget.text = etBudget.text.toString()
            projectAbout.text = etAbout.text.toString()

            alertDialog.dismiss()
        }

    }

    private fun updateProjectData(
        id:String,
        ptitle: String,
        pbudget: String,
        pabout: String
        // Update the project data in the Firebase Realtime Database
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Project").child(id)
        val projectInfo = Project(id,ptitle,pbudget,pabout)
        dbRef.setValue(projectInfo)
    }

}