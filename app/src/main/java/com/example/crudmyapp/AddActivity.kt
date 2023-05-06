package com.example.crudmyapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class AddActivity : AppCompatActivity() {
    var itemName: EditText? = null
    var price: EditText? = null
    var description: EditText? = null
    var iImage: EditText? = null
    var btnAdd: Button? = null
    var btnBack: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)


       /* var btnBack = findViewById<Button>(R.id.btnBack)
        btnBack.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)


        }

        */

        itemName = findViewById<View>(R.id.txtItemName) as EditText
        price = findViewById<View>(R.id.txtPrice) as EditText
        description = findViewById<View>(R.id.txtDescription) as EditText
        iImage = findViewById<View>(R.id.txtImageUrl) as EditText
        btnAdd = findViewById<View>(R.id.btnAdd) as Button
        btnBack = findViewById<View>(R.id.btnBack) as Button
        btnAdd!!.setOnClickListener {
            insertData()
            clearAll()
        }
        btnBack!!.setOnClickListener { finish() }
    }

    private fun insertData() {
        val map: MutableMap<String, Any> = HashMap()
        map["itemName"] = itemName!!.text.toString()
        map["price"] = price!!.text.toString()
        map["description"] = description!!.text.toString()
        map["iImage"] = iImage!!.text.toString()
        FirebaseDatabase.getInstance().reference.child("Items").push()
            .setValue(map)
            .addOnSuccessListener {
                Toast.makeText(
                    this@AddActivity,
                    "Data added successfully",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .addOnFailureListener {
                Toast.makeText(
                    this@AddActivity,
                    "Error while inserting!",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun clearAll() {
        itemName!!.setText("")
        price!!.setText("")
        description!!.setText("")
        iImage!!.setText("")
    }
}