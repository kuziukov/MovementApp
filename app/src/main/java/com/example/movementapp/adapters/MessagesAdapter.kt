package com.example.movementapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.movementapp.R
import com.example.movementapp.database.entity.MessageEntity
import com.example.movementapp.models.Message
import com.example.movementapp.utils.numberToLong
import java.text.SimpleDateFormat
import java.util.*

class MessagesAdapter (private var mMessages: List<MessageEntity>) : RecyclerView.Adapter<MessagesAdapter.ViewHolder>()
{
    val MESSAGE_TYPE_ONE = 0
    val MESSAGE_TYPE_TWO = 1

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val message_body = itemView.findViewById<TextView>(R.id.message_body)
        val messageTime = itemView.findViewById<TextView>(R.id.messageTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessagesAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        if (viewType == MESSAGE_TYPE_ONE){
            val messagesView = inflater.inflate(R.layout.list_item_message_sent, parent, false)
            return ViewHolder(messagesView)
        } else if (viewType == MESSAGE_TYPE_TWO) {
            val messagesView = inflater.inflate(R.layout.list_item_message_received, parent, false)
            return ViewHolder(messagesView)
        }
        return ViewHolder(View(null))
    }

    override fun onBindViewHolder(viewHolder: MessagesAdapter.ViewHolder, position: Int) {
        val itemType: Int = getItemViewType(position)
        val message: MessageEntity = mMessages.get(position)
        // Set item views based on your views and data model
        val date = SimpleDateFormat("HH:mm").format(Date(numberToLong(message.created_at!!)))

        val message_body = viewHolder.message_body
        message_body.text = message.message
        val messageTime = viewHolder.messageTime
        messageTime.text = date
    }

    override fun getItemViewType(position: Int): Int {
        return if (mMessages.get(position).user?._id == "5fbd8e82c18aa4ad676da4b0") {
            MESSAGE_TYPE_ONE
        } else {
            MESSAGE_TYPE_TWO
        }
    }

    override fun getItemCount(): Int {
        return mMessages.size
    }

    fun setChatMessages(chatMessages: List<MessageEntity>) {
        mMessages = chatMessages.sortedBy{ numberToLong(it.created_at!!) }
        notifyDataSetChanged()
    }

    fun addMessage(chatMessage: MessageEntity) {
        this.mMessages.toMutableList().add(chatMessage)
        notifyDataSetChanged()
    }

    fun deleteMessage(position: Int) {
        this.mMessages.toMutableList().removeAt(position)
        notifyDataSetChanged()
    }

}