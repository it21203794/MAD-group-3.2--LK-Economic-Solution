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

        var imageButton = findViewById<ImageButton>(R.id.imageButton)
        imageButton.setOnClickListener {
            val intent = Intent(this, create_new_project::class.java)
            startActivity(intent)

        }

            projectRecyclerView = findViewById(R.id.projectList)
            rtvLoadingData = findViewById(R.id.rtvLoadingData)
            projectRecyclerView.layoutManager = LinearLayoutManager(this)
            projectRecyclerView.setHasFixedSize(true)

            projectList = arrayListOf<Project>()

            getProjectsData()

        }
        private fun getProjectsData(){
            projectRecyclerView.visibility = View.GONE
            rtvLoadingData.visibility = View.VISIBLE

            dbRef = FirebaseDatabase.getInstance().getReference("Project")

            dbRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    projectList.clear()
                    if (snapshot.exists()){
                        for (projectSnap in snapshot.children){
                            val projectData = projectSnap.getValue(Project::class.java)
                            projectList.add(projectData!!)
                        }
                        val mAdapter = ProjectAdapter(projectList)
                        projectRecyclerView.adapter = mAdapter

                        mAdapter.setOnItemClickListener(object : ProjectAdapter.onItemClickListener{
                            override fun onItemClick(position: Int) {
                                val intent = Intent(this@post_a_project, project_view::class.java)
                                //put extras
                                intent.putExtra("projectId", projectList[position].projectId)
                                intent.putExtra("ptitle", projectList[position].ptitle)
                                intent.putExtra("pbudget", projectList[position].pbudget)
                                intent.putExtra("pabout", projectList[position].pabout)
                                startActivity(intent)

                            }

                        })



                        projectRecyclerView.visibility = View.VISIBLE
                        rtvLoadingData.visibility = View.GONE
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

        }
}
