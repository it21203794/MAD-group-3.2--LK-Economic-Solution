package com.example.myapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.myapp.Sign_Up
import com.example.myapp.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Sign_In : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        binding.textView.setOnClickListener {
            val intent = Intent(this, Sign_Up::class.java)
            startActivity(intent)
        }

        binding.button.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val pass = binding.passET.text.toString()

            //checking its admin account
            fun checkUser() {
                val firebaseUser = firebaseAuth.currentUser!!
                val ref = FirebaseDatabase.getInstance().getReference("Users")

                ref.child(firebaseUser.uid)
                    .addListenerForSingleValueEvent(object: ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val userType= snapshot.child("email").value
                            //Log.d("checkUser", "email: $userType")
                            if (userType == "admin@gmail.com"){
                                startActivity(Intent(this@Sign_In, Fetching::class.java))
                                finish()
                            }
                        }
                        override fun onCancelled(error: DatabaseError) {
                        }
                    } )
            }

            if (email.isNotEmpty() && pass.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(email,pass)
                    .addOnSuccessListener{
                        if (email == "admin@gmail.com"){
                            startActivity(Intent(this@Sign_In, AdminSide::class.java))
                            finish()
                        }
                    }
                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                        if (it.isSuccessful) {
                            val intent = Intent(this, Main_home::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()

                         }
                }

            } else {
                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()

            }
        }
    }
}
















