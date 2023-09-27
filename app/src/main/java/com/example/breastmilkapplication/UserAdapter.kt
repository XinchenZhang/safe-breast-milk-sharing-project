package com.example.breastmilkapplication

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class UserAdapter(val context: Context, val userList: ArrayList<User>): RecyclerView.Adapter<UserAdapter.UserViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.user_layout, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser = userList[position]

        holder.textName.text = currentUser.name

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ChatRoomActivity::class.java)
            intent.putExtra("name", currentUser.name)
            intent.putExtra("uid", currentUser.uid)
            context.startActivity(intent)
        }

        holder.buttonDirection.setOnClickListener {  // Set click listener for the button
            // Handle button click
//            Toast.makeText(context, "Button clicked for user: ${currentUser.name}", Toast.LENGTH_SHORT).show()
            val intent = Intent(context, MapActivity::class.java)
            intent.putExtra("name", currentUser.name)
            intent.putExtra("uid", currentUser.uid)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class UserViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var textName = itemView.findViewById<TextView>(R.id.txt_name)
        var buttonDirection = itemView.findViewById<TextView>(R.id.btn_direction)
    }
}