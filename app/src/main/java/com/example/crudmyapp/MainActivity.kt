package com.example.crudmyapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    var recyclerView: RecyclerView? = null
    var mainAdapter: MainAdapter? = null
    var floatingActionButton: FloatingActionButton? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById<View>(R.id.rv) as RecyclerView
        recyclerView!!.layoutManager = LinearLayoutManager(this)
        val options = FirebaseRecyclerOptions.Builder<MainModel>()
            .setQuery(
                FirebaseDatabase.getInstance().reference.child("Items"),
                MainModel::class.java
            )
            .build()
        mainAdapter = MainAdapter(options)
        recyclerView!!.adapter = mainAdapter
        floatingActionButton = findViewById<View>(R.id.floatingActionButton) as FloatingActionButton
        floatingActionButton!!.setOnClickListener {
            startActivity(
                Intent(
                    applicationContext, AddActivity::class.java
                )
            )
        }
    }

    override fun onStart() {
        super.onStart()
        mainAdapter!!.startListening()
    }

    override fun onStop() {
        super.onStop()
        mainAdapter!!.stopListening()
    }
}