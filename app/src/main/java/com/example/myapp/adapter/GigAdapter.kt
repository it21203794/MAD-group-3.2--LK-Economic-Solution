package com.example.myapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.R
import com.example.myapp.models.Gig

class GigAdapter (private val gigList: ArrayList<Gig>) : RecyclerView.Adapter<GigAdapter.ViewHolder>(){

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.gigs, parent, false)
        return ViewHolder(itemView, mListener )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentGig = gigList[position]
        holder.gigCategory.text = currentGig.category
        holder.gigMainSkill.text = currentGig.mainSkill
    }

    override fun getItemCount(): Int {
        return gigList.size
    }
    class ViewHolder( itemView: View, clickListener: onItemClickListener) : RecyclerView.ViewHolder(itemView){

        val gigCategory : TextView = itemView.findViewById(R.id.gigCategory)
        val gigMainSkill : TextView = itemView.findViewById(R.id.gigMainSkill)

        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }
    }

}