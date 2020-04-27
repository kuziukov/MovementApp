package com.example.movementapp.ui.chat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.movementapp.R

class ChatAdapter (private val context: Context,
       private val dataList: ArrayList<String>) : BaseAdapter() {
    private val inflater: LayoutInflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    override fun getCount(): Int { return dataList.size }
    override fun getItem(position: Int): Int { return position }
    override fun getItemId(position: Int): Long { return position.toLong() }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var dataitem = dataList[position]


        val rowView = getCustomView(position, parent)

        rowView!!.tag = position
        return rowView
    }

    fun getCustomView(position: Int, parent: ViewGroup): View? {
        if((position % 2) == 0){
            return inflater.inflate(R.layout.list_item_message_sent, parent, false)
        }
        else {
            return inflater.inflate(R.layout.list_item_message_received, parent, false)
        }
    }
}