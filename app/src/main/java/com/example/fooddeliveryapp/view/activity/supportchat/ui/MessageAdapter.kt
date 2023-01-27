package com.example.fooddeliveryapp.view.activity.supportchat.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.model.remote.Constants.RECEIVE_ID
import com.example.fooddeliveryapp.model.remote.Constants.SEND_ID
import com.example.fooddeliveryapp.view.activity.supportchat.data.Message

class MessageAdapter: RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    var messageList = mutableListOf<Message>()


    inner class MessageViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val senderMessage = view.findViewById<TextView>(R.id.sender_message)
        val botMessage = view.findViewById<TextView>(R.id.receive_message)
        init{
            itemView.setOnClickListener{
                messageList.removeAt(adapterPosition)
                notifyItemRemoved(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.message_item,parent,false)
        return MessageViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val currentMessage = messageList[position]
        when(currentMessage.id){
            SEND_ID ->{
                holder.senderMessage.apply{
                    text = currentMessage.message
                    visibility = View.VISIBLE
                }
                holder.botMessage.visibility = View.GONE
            }
            RECEIVE_ID ->{
                holder.botMessage.apply{
                    text = currentMessage.message
                    visibility = View.VISIBLE
                }
                holder.senderMessage.visibility = View.GONE
            }
        }
    }

    override fun getItemCount() = messageList.size

    fun insertMessage(message:Message){
        this.messageList.add(message)
        notifyItemInserted(messageList.size)
    }
}