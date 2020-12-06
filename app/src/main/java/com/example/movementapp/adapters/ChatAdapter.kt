package com.example.movementapp.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.movementapp.R
import com.example.movementapp.models.Message
import com.example.movementapp.utils.numberToLong
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ChatAdapter(
    private val context: Context,
    private val dataList: ArrayList<Message>
) : BaseAdapter() {
    private val inflater: LayoutInflater =
        this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return dataList.size
    }

    override fun getItem(position: Int): Int {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("SimpleDateFormat")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val dataitem = dataList[position]

        val rowView = getCustomView(position, parent)
        val message_body = rowView?.findViewById(R.id.message_body) as TextView
        val messageTime = rowView.findViewById(R.id.messageTime) as TextView
        val date = SimpleDateFormat("HH:mm").format(Date(numberToLong(dataitem.created_at!!)))

        message_body.text = dataitem.message
        messageTime.text = date

        rowView.tag = position
        return rowView
    }

    fun getCustomView(position: Int, parent: ViewGroup): View? {
        val message = dataList[position]
        if(message.user!!._id == "5fb6b9cea9f478e1337c0e8b"){
            return inflater.inflate(R.layout.list_item_message_sent, parent, false)
        }
        return inflater.inflate(R.layout.list_item_message_received, parent, false)
    }
}