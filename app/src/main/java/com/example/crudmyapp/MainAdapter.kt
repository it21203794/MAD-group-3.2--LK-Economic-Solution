package com.example.crudmyapp

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.crudmyapp.MainAdapter.myViewHolder
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
import com.orhanobut.dialogplus.DialogPlus
import com.orhanobut.dialogplus.ViewHolder
import de.hdodenhof.circleimageview.CircleImageView

class MainAdapter
/**
 * Initialize a [RecyclerView.Adapter] that listens to a Firebase query. See
 * [FirebaseRecyclerOptions] for configuration options.
 *
 * @param options
 */
    (options: FirebaseRecyclerOptions<MainModel?>) :
    FirebaseRecyclerAdapter<MainModel, myViewHolder>(options) {
    override fun onBindViewHolder(
        holder: myViewHolder,
        @SuppressLint("RecyclerView") position: Int,
        model: MainModel
    ) {
        holder.itemName.text = model.itemName
        holder.price.text = model.price
        holder.description.text = model.description
        Glide.with(holder.img.context)
            .load(model.iImage)
            .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
            .circleCrop()
            .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark_normal)
            .into(holder.img)
        holder.btnEdit.setOnClickListener {
            val dialogPlus = DialogPlus.newDialog(holder.img.context)
                .setContentHolder(ViewHolder(R.layout.update_popup))
                .setExpanded(true, 1200)
                .create()

            dialogPlus.show();
            val view = dialogPlus.footerView
            val itemName = view.findViewById<EditText>(R.id.txtItemName)
            val price = view.findViewById<EditText>(R.id.txtPrice)
            val description = view.findViewById<EditText>(R.id.txtDescription)
            val iImage = view.findViewById<EditText>(R.id.txtImageUrl)
            val btnUpdate = view.findViewById<Button>(R.id.btnUpdate)
            itemName.setText(model.itemName)
            price.setText(model.price)
            description.setText(model.description)
            iImage.setText(model.iImage)
            dialogPlus.show()
            btnUpdate.setOnClickListener {
                val map: MutableMap<String, Any> = HashMap()
                map["itemName"] = itemName.text.toString()
                map["price"] = price.text.toString()
                map["description"] = description.text.toString()
                FirebaseDatabase.getInstance().reference.child("Items")
                    .child(getRef(position).key!!).updateChildren(map)
                    .addOnSuccessListener {
                        Toast.makeText(
                            holder.itemName.context,
                            "Data Updated successfully!",
                            Toast.LENGTH_SHORT
                        ).show()
                        dialogPlus.dismiss()
                    }
                    .addOnFailureListener {
                        Toast.makeText(
                            holder.itemName.context,
                            "Error while updating",
                            Toast.LENGTH_SHORT
                        ).show()
                        dialogPlus.dismiss()
                    }
            }
        }
        holder.btnDelete.setOnClickListener {
            val builder = AlertDialog.Builder(holder.itemName.context)
            builder.setTitle("Are you sure?")
            builder.setMessage("Deleted data can't be Undo.")
            builder.setPositiveButton("Delete") { dialogInterface, which ->
                FirebaseDatabase.getInstance().reference.child("Items")
                    .child(getRef(position).key!!).removeValue()
            }
            builder.setNegativeButton("Cancel") { dialogInterface, which ->
                Toast.makeText(
                    holder.itemName.context,
                    "Cancel",
                    Toast.LENGTH_SHORT
                ).show()
            }
            builder.show()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.main_item, parent, false)
        return myViewHolder(view)
    }

    inner class myViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var img: CircleImageView
        var itemName: TextView
        var price: TextView
        var description: TextView
        var btnEdit: Button
        var btnDelete: Button

        init {
            img = itemView.findViewById<View>(R.id.img1) as CircleImageView
            itemName = itemView.findViewById<View>(R.id.itemNametext) as TextView
            price = itemView.findViewById<View>(R.id.pricetext) as TextView
            description = itemView.findViewById<View>(R.id.descriptiontext) as TextView
            btnEdit = itemView.findViewById<View>(R.id.btnEdit) as Button
            btnDelete = itemView.findViewById<View>(R.id.btnDelete) as Button
        }
    }
}