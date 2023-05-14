package com.example.myapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

class dashboard : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard)

        var imageView4 = findViewById<ImageButton>(R.id.imageView4)
        imageView4.setOnClickListener{
            val intent = Intent(this, find_all_projects::class.java)
            startActivity(intent)

        }

        var imageView5 = findViewById<ImageButton>(R.id.imageView5)
        imageView5.setOnClickListener{
            val intent = Intent(this, find_freelancers::class.java)
            startActivity(intent)

        }

        var imageView6 = findViewById<ImageButton>(R.id.imageView6)
        imageView6.setOnClickListener{
            val intent = Intent(this, post_a_project::class.java)
            startActivity(intent)

        }

        var imageView7 = findViewById<ImageButton>(R.id.imageView7)
        imageView7.setOnClickListener{
            val intent = Intent(this, become_a_freelancer::class.java)
            startActivity(intent)

        }

        var imageButton = findViewById<ImageButton>(R.id.imageButton)
        imageButton.setOnClickListener{
            val intent = Intent(this, create_new_project::class.java)
            startActivity(intent)

        }
        var imageView37 = findViewById<ImageButton>(R.id.imageView37)
        imageView37.setOnClickListener{
            val intent = Intent(this, home_page::class.java)
            startActivity(intent)

        }



    }
}