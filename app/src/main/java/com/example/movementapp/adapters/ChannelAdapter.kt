package com.example.movementapp.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.movementapp.R
import com.example.movementapp.database.entity.ChatEntity
import com.example.movementapp.utils.numberToLong
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ChannelAdapter(private val context: Context, private var dataList: ArrayList<ChatEntity>) :
    BaseAdapter() {

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


    fun setChats(chats: List<ChatEntity>){
        this.dataList = chats as ArrayList<ChatEntity>
        notifyDataSetChanged()
    }

    fun getChat(position: Int): ChatEntity {
        return this.dataList[position]
    }


    @SuppressLint("SimpleDateFormat", "ViewHolder", "SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var chatitem = dataList[position]

        val rowView = inflater.inflate(R.layout.list_item_channel, parent, false)
        val nameTextView = rowView.findViewById<TextView>(R.id.chat_name_txt)
        val chat_description = rowView.findViewById<TextView>(R.id.chat_description)
        val chat_date_txt = rowView.findViewById<TextView>(R.id.chat_date_txt)
        val chat_last_message_status = rowView.findViewById<ImageView>(R.id.chat_last_message_status)
        val chat_notifs_txt = rowView.findViewById<TextView>(R.id.chat_notifs_txt)

        nameTextView.setText(chatitem.name)
        chat_notifs_txt.visibility = View.GONE

        if(chatitem.last_message == null){
            chat_description.text = "Сообщений пока нет"
        } else {
            chat_last_message_status.visibility = View.VISIBLE
            chat_description.text = chatitem.last_message?.message
        }

        if (chatitem.last_message != null) {
            val date = SimpleDateFormat("HH:mm").format(Date(numberToLong(chatitem.last_message!!.created_at!!)))
            chat_date_txt.text = date
        } else {
            chat_date_txt.text = ""
        }
        rowView.tag = position
        return rowView
    }

}