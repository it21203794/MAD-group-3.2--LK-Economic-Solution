package com.example.myapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.adapter.ProjectAdapter
import com.example.myapp.models.Gig
import com.example.myapp.models.Project
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class find_all_projects : AppCompatActivity() {

    private lateinit var projectRecyclerView: RecyclerView
    private lateinit var rtvLoadingData: TextView
    private lateinit var projectList: ArrayList<Project>
    private lateinit var dbRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.find_all_projects)

        // Initialize views
        projectRecyclerView = findViewById(R.id.projectList)
        rtvLoadingData = findViewById(R.id.rtvLoadingData)
        projectRecyclerView.layoutManager = LinearLayoutManager(this)
        projectRecyclerView.setHasFixedSize(true)

        // Initialize project list
        projectList = arrayListOf<Project>()

        // Fetch project data from Firebase database
        getProjectsData()
    }

    private fun getProjectsData(){
        // Hide RecyclerView and show loading text
        projectRecyclerView.visibility = View.GONE
        rtvLoadingData.visibility = View.VISIBLE

        // Get a reference to the "Project" node in the Firebase database
        dbRef = FirebaseDatabase.getInstance().getReference("Project")

        // Add a ValueEventListener to listen for changes in the data
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                projectList.clear()
                if (snapshot.exists()){
                    // Iterate through each child node (project) in the snapshot
                    for (projectSnap in snapshot.children){
                        // Get the project data and add it to the project list
                        val projectData = projectSnap.getValue(Project::class.java)
                        projectList.add(projectData!!)
                    }
                    // Create an instance of the custom adapter and set it to the RecyclerView
                    val mAdapter = ProjectAdapter(projectList)
                    projectRecyclerView.adapter = mAdapter

                    // Set click listener for the items in the RecyclerView
                    mAdapter.setOnItemClickListener(object : ProjectAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            // Create an intent to navigate to the selected project's details
                            val intent = Intent(this@find_all_projects, find_selected_project::class.java)
                            //put extras
                            intent.putExtra("projectId", projectList[position].projectId)
                            intent.putExtra("ptitle", projectList[position].ptitle)
                            intent.putExtra("pbudget", projectList[position].pbudget)
                            intent.putExtra("pabout", projectList[position].pabout)
                            // Start the activity with the intent
                            startActivity(intent)

                        }

                    })


                    // Show RecyclerView and hide loading text
                    projectRecyclerView.visibility = View.VISIBLE
                    rtvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
                // Handle database error (not implemented in this code)
            }

        })

    }

}