package com.example.myapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myapp.databinding.SignInBinding
import com.google.firebase.auth.FirebaseAuth

class sign_in : AppCompatActivity() {

    private lateinit var binding: SignInBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get an instance of FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()
        // Set click listener for "textView" element
        binding.textView.setOnClickListener {
            // Create an intent to navigate to the sign_up activity
            val intent = Intent(this, sing_up::class.java)
            startActivity(intent)
        }




        // Set click listener for "button" element
        binding.button.setOnClickListener {
            // Retrieve email and password from input fields
            val email = binding.emailEt.text.toString()
            val pass = binding.passET.text.toString()

            // Check if email and password are not empty
            if (email.isNotEmpty() && pass.isNotEmpty()) {

                // Sign in with email and password using FirebaseAuth
                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        // Sign-in successful, navigate to home_page activity
                        val intent = Intent(this, home_page::class.java)
                        startActivity(intent)
                    } else {
                        // Sign-in failed, display the exception message as a toast
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()

                    }
                }

            } else {
                // Display a toast if any of the fields are empty
                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()

            }
        }
    }
}
















