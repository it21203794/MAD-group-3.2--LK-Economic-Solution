package com.example.myapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.adapter.ProjectAdapter
import com.example.myapp.models.Project
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class post_a_project : AppCompatActivity() {

    private lateinit var projectRecyclerView: RecyclerView
    private lateinit var rtvLoadingData: TextView
    private lateinit var projectList: ArrayList<Project>
    private lateinit var dbRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.post_a_project)

        // Find the ImageButton and set a click listener to navigate to create_new_project activity
        var imageButton = findViewById<ImageButton>(R.id.imageButton)
        imageButton.setOnClickListener {
            val intent = Intent(this, create_new_project::class.java)
            startActivity(intent)

        }

        // Initialize RecyclerView, TextView, and projectList
            projectRecyclerView = findViewById(R.id.projectList)
            rtvLoadingData = findViewById(R.id.rtvLoadingData)
            projectRecyclerView.layoutManager = LinearLayoutManager(this)
            projectRecyclerView.setHasFixedSize(true)

            projectList = arrayListOf<Project>()

        // Fetch projects data from Firebase Realtime Database
            getProjectsData()

        }
        private fun getProjectsData(){
            // Hide the projectRecyclerView and show the loading text while fetching data
            projectRecyclerView.visibility = View.GONE
            rtvLoadingData.visibility = View.VISIBLE

            // Get a reference to the "Project" node in the Firebase Realtime Database
            dbRef = FirebaseDatabase.getInstance().getReference("Project")

            // Add a ValueEventListener to listen for changes in the data at the "Project" node

            dbRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    // Clear the projectList before adding new data
                    projectList.clear()
                    if (snapshot.exists()){
                        // Iterate through the children of the snapshot to retrieve project data
                        for (projectSnap in snapshot.children){
                            val projectData = projectSnap.getValue(Project::class.java)
                            projectList.add(projectData!!)
                        }
                        // Create an instance of the ProjectAdapter and set it as the adapter for the projectRecyclerView
                        val mAdapter = ProjectAdapter(projectList)
                        projectRecyclerView.adapter = mAdapter

                        mAdapter.setOnItemClickListener(object : ProjectAdapter.onItemClickListener{
                            override fun onItemClick(position: Int) {
                                // Create an intent to navigate to project_view activity and pass relevant data as extras
                                val intent = Intent(this@post_a_project, project_view::class.java)
                                //put extras
                                intent.putExtra("projectId", projectList[position].projectId)
                                intent.putExtra("ptitle", projectList[position].ptitle)
                                intent.putExtra("pbudget", projectList[position].pbudget)
                                intent.putExtra("pabout", projectList[position].pabout)
                                startActivity(intent)

                            }

                        })






                        // Show the projectRecyclerView and hide the loading text after fetching and setting the data
                        projectRecyclerView.visibility = View.VISIBLE
                        rtvLoadingData.visibility = View.GONE
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle the onCancelled event if it occurs
                    TODO("Not yet implemented")
                }

            })

        }
}
