

package com.example.myapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapp.databinding.SingUpBinding
import com.google.firebase.auth.FirebaseAuth

class sing_up : AppCompatActivity() {

    private lateinit var binding: SingUpBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the layout using the generated binding class
        binding = SingUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        // Set a click listener for the "Sign In" textView
        binding.textView.setOnClickListener {
            val intent = Intent(this, sign_in::class.java)
            startActivity(intent)
        }
        // Set a click listener for the "Sign Up" button
        binding.button.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val pass = binding.passET.text.toString()
            val confirmPass = binding.confirmPassEt.text.toString()

            // Check if all fields are   non-empty



            if (email.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty()) {
                // Check if the password matches the confirmed password
                if (pass == confirmPass) {

                    // Create a user with the provided email and password using FirebaseAuth
                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                        if (it.isSuccessful) {
                            // If user creation is successful, navigate to the sign-in activity
                            val intent = Intent(this, sign_in::class.java)
                            startActivity(intent)
                        } else {
                            // If user creation fails, display the exception message as a Toast
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()

                        }
                    }
                } else {
                    // If the password and confirmed password do not match, display an error Toast
                    Toast.makeText(this, "Password is not matching", Toast.LENGTH_SHORT).show()
                }
            } else {
                // If any of the fields are empty, display an error Toast
                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()

            }
        }
    }
}