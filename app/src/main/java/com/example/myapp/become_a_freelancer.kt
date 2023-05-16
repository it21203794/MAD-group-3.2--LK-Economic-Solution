package com.example.myapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.adapter.GigAdapter
import com.example.myapp.models.Gig
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class become_a_freelancer : AppCompatActivity() {

    // Declaring variables
    private lateinit var gigRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var gigList: ArrayList<Gig>
    private lateinit var dbRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.become_a_freelancer)
        // Get reference to the ImageButton and set a click listener
        var imageButton = findViewById<ImageButton>(R.id.imageButton)
        imageButton.setOnClickListener{
            // Create an intent to navigate to create_new_gig activity
            val intent = Intent(this, create_new_gig::class.java)
            startActivity(intent)
        }

        // Initialize RecyclerView and other views
        gigRecyclerView = findViewById(R.id.gigList)
        tvLoadingData = findViewById(R.id.tvLoadingData)
        gigRecyclerView.layoutManager = LinearLayoutManager(this)
        gigRecyclerView.setHasFixedSize(true)

        // Initialize gigList as an empty ArrayList
        gigList = arrayListOf<Gig>()

        // Fetch gig data from Firebase
        getGigsData()
    }
    private fun getGigsData(){
        // Hide RecyclerView and show loading message
        gigRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        // Get reference to the "Gig" node in Firebase database
        dbRef = FirebaseDatabase.getInstance().getReference("Gig")

        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                gigList.clear()
                if (snapshot.exists()){
                    // Iterate over each child snapshot and convert to Gig object
                    for (gigSnap in snapshot.children){
                        val gigData = gigSnap.getValue(Gig::class.java)
                        gigList.add(gigData!!)
                    }
                    // Create      and set the adapter for the RecyclerView
                    val mAdapter = GigAdapter(gigList)
                    gigRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : GigAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            // Create an intent to navigate to gig_view activity
                            val intent = Intent(this@become_a_freelancer, gig_view::class.java)
                            //put extras
                            intent.putExtra("gigId", gigList[position].gigId)
                            intent.putExtra("category", gigList[position].category)
                            intent.putExtra("mainSkill", gigList[position].mainSkill)
                            intent.putExtra("about12", gigList[position].about12)
                            startActivity(intent)

                        }

                    })
                    // Show RecyclerView and hide loading message
                    gigRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
                // Handle database error (not implemented in this code)
                // You can add your own error handling logic here
            }

        })
        // Get reference to the ImageView and set a click listener
        var imageView40 = findViewById<ImageButton>(R.id.imageView40)
        imageView40.setOnClickListener{
            // Create an intent to navigate to dashboard activity
            val intent = Intent(this, dashboard::class.java)
            startActivity(intent)

        }
    }

}