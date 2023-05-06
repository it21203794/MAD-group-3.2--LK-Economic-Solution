package com.example.myapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.R
import com.example.myapp.adapters.aplAdapter
import com.example.myapp.models.applicantModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Fetching : AppCompatActivity() {

    private lateinit var aplRecyclerView: RecyclerView
    private lateinit var tvLoadingData:TextView
    private lateinit var aplList: ArrayList<applicantModel>
    private lateinit var dbRef : DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetching)

        aplRecyclerView =findViewById(R.id.rvApplicant)
        aplRecyclerView.layoutManager =LinearLayoutManager(this)
         aplRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvload)

        aplList = arrayListOf<applicantModel>()

        getApplicantsData()
    }

    private fun getApplicantsData(){
        aplRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("applicant")

        dbRef.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                aplList.clear()
                if(snapshot.exists()){
                    for(aplSnap in snapshot.children){
                        val aplData =aplSnap.getValue(applicantModel::class.java)
                        aplList.add(aplData!!)
                    }

                    val mAdapter = aplAdapter(aplList)
                    aplRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object:aplAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@Fetching, applicant_details::class.java)

                            //put extras
                            intent.putExtra("aplID",aplList[position].applicantId)
                            intent.putExtra("aplName",aplList[position].applicant_name)
                            intent.putExtra("aplEmail",aplList[position].applicant_email)
                            intent.putExtra("aplPhone",aplList[position].applicant_phone)
                            intent.putExtra("aplEdu",aplList[position].applicant_education)
                            startActivity(intent)


                        }

                    })

                    aplRecyclerView.visibility =View.VISIBLE
                    tvLoadingData.visibility =View.GONE
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

}