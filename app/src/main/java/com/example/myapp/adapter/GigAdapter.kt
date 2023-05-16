package com.example.myapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.R
import com.example.myapp.models.Gig
// The GigAdapter class is responsible for populating a RecyclerView with a list of Gig objects.
class GigAdapter (private val gigList: ArrayList<Gig>) : RecyclerView.Adapter<GigAdapter.ViewHolder>(){

    private lateinit var mListener: onItemClickListener


    // Interface for defining the item click listener
    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    // Method to set the item click listener
    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener = clickListener
    }

    // Create and return a new ViewHolder object
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Inflate the layout for each item in the RecyclerView
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.gigs, parent, false)
        return ViewHolder(itemView, mListener )

    }

    // Bind data to the ViewHolder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Set the data to the corresponding views in the ViewHolder
        val currentGig = gigList[position]
        holder.gigCategory.text = currentGig.category
        holder.gigMainSkill.text = currentGig.mainSkill
        holder.gigAbout12.text = currentGig.about12
    }

    // Return the number of items in the list
    override fun getItemCount(): Int {
        return gigList.size
    }
    // ViewHolder class that holds references to the views in each item of the RecyclerView
    class ViewHolder( itemView: View, clickListener: onItemClickListener) : RecyclerView.ViewHolder(itemView){

        val gigCategory : TextView = itemView.findViewById(R.id.gigCategory)
        val gigMainSkill : TextView = itemView.findViewById(R.id.gigMainSkill)
        val gigAbout12 : TextView = itemView.findViewById(R.id.gigAbout12)

        init {
            // Set a click listener for the ViewHolder item
            itemView.setOnClickListener {
                // Notify the listener when the item is clicked and pass the item position
                clickListener.onItemClick(adapterPosition)
            }
        }
    }

}