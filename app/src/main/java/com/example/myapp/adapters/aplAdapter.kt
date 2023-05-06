package com.example.myapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.R
import com.example.myapp.models.applicantModel

class aplAdapter(private val aplList:ArrayList<applicantModel>):
    RecyclerView.Adapter<aplAdapter.ViewHolder>(){

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.applicant_demo,parent, false)
        return ViewHolder(itemView,mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentapl =aplList[position]
        holder.tvAplName.text = currentapl.applicant_name
    }

    override fun getItemCount(): Int {
        return aplList.size
    }

    class ViewHolder(itemView: View,clickListener: onItemClickListener):RecyclerView.ViewHolder(itemView){

        val tvAplName : TextView = itemView.findViewById(R.id.tvnama)

        init {
            itemView.setOnClickListener{
                clickListener.onItemClick(adapterPosition)
            }
        }
    }

}
