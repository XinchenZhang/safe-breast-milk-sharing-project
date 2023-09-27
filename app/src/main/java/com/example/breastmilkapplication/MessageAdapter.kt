package com.example.breastmilkapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(val context: Context, val massageList: ArrayList<Message>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val ITEM_SENT = 1
    val ITEM_RECEIVED = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == ITEM_SENT) {

            val view = LayoutInflater.from(context).inflate(R.layout.sent, parent, false)
            return SentViewHolder(view)
        } else {
            val view = LayoutInflater.from(context).inflate(R.layout.received, parent, false)
            return ReceivedViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMassage = massageList[position]
        if(holder.javaClass == SentViewHolder::class.java) {
            val sentViewHolder = holder as SentViewHolder
            holder.sentMessageText.text = currentMassage.message

        } else {
            val receivedViewHolder = holder as ReceivedViewHolder
            holder.receiveMessageText.text = currentMassage.message
        }
    }

    override fun getItemCount(): Int {
        return massageList.size
    }

    override fun getItemViewType(position: Int): Int {
        val currentMassage = massageList[position]
        if(FirebaseAuth.getInstance().currentUser?.uid == currentMassage.senderId) {
            return ITEM_SENT
        } else {
            return ITEM_RECEIVED
        }
    }


    class SentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var sentMessageText: TextView = itemView.findViewById(R.id.txt_sent_message)
    }

    class ReceivedViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var receiveMessageText: TextView = itemView.findViewById(R.id.txt_receive_message)
    }

}