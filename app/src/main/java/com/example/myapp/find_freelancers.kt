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

class find_freelancers : AppCompatActivity() {

    // Declare required variables
    private lateinit var gigRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var gigList: ArrayList<Gig>
    private lateinit var dbRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.find_freelancers)

        // Initialize the RecyclerView and loading TextView
        gigRecyclerView = findViewById(R.id.gigList)
        tvLoadingData = findViewById(R.id.tvLoadingData)
        gigRecyclerView.layoutManager = LinearLayoutManager(this)
        gigRecyclerView.setHasFixedSize(true)

        // Initialize the gigList as an empty ArrayList
        gigList = arrayListOf<Gig>()

        // Retrieve gigs data
        getGigsData()
    }
    // Function to retrieve gigs data from      the database
    private fun getGigsData() {
        // Hide the RecyclerView and show loading text
        gigRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        // Get a reference to the "Gig" node in the Firebase database
        dbRef = FirebaseDatabase.getInstance().getReference("Gig")

        // Get a reference to the "Gig" node in the Firebase database
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                gigList.clear()
                if (snapshot.exists()) {
                    for (gigSnap in snapshot.children) {
                        val gigData = gigSnap.getValue(Gig::class.java)
                        gigList.add(gigData!!)
                    }
                    val mAdapter = GigAdapter(gigList)
                    gigRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : GigAdapter.onItemClickListener {
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@find_freelancers, find_gigs::class.java)
                            //put extras
                            intent.putExtra("gigId", gigList[position].gigId)
                            intent.putExtra("category", gigList[position].category)
                            intent.putExtra("mainSkill", gigList[position].mainSkill)
                            intent.putExtra("about12", gigList[position].about12)
                            startActivity(intent)

                        }

                    })

                    gigRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }
}