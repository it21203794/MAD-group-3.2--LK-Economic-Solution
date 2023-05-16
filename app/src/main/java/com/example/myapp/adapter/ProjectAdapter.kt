package com.example.myapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.R
import com.example.myapp.models.Project

class ProjectAdapter (private val projectList: ArrayList<Project>) : RecyclerView.Adapter<ProjectAdapter.ViewHolder>(){

            private lateinit var mListener: onItemClickListener

            interface onItemClickListener{
                fun onItemClick(position: Int)
            }

            fun setOnItemClickListener(clickListener: onItemClickListener){
                mListener = clickListener
            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                val itemView = LayoutInflater.from(parent.context).inflate(R.layout.projects, parent, false)
                return ViewHolder(itemView, mListener )

            }

            override fun onBindViewHolder(holder:ViewHolder, position: Int) {
                val currentProject = projectList[position]
                holder.projectTitle.text = currentProject.ptitle
                holder.projectBudget.text = currentProject.pbudget
                holder.projectAbout.text = currentProject.pabout

            }







            override fun getItemCount(): Int {
                return projectList.size
            }
            class ViewHolder(itemView: View, clickListener: onItemClickListener) : RecyclerView.ViewHolder(itemView){

                val projectTitle : TextView = itemView.findViewById(R.id.projectTitle)
                val projectBudget : TextView = itemView.findViewById(R.id.projectBudget)
                val projectAbout : TextView = itemView.findViewById(R.id.projectAbout)

                init {
                    itemView.setOnClickListener {
                        clickListener.onItemClick(adapterPosition)
                    }
                }
            }

}