package com.example.movementapp.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.movementapp.R

class DialogChannelFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_channel_dialog, container)
        var pioneers = arrayOf(
            "Disable notifications",
            "Clean history"
        )

        val myListView = rootView.findViewById(R.id.channel_listview_dialog) as ListView

        myListView!!.adapter =
            ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, pioneers)

        this.dialog.setTitle("Tech Pioneers")

        myListView.setOnItemClickListener { adapterView,
                                            view,
                                            position,
                                            l
            ->
            Toast.makeText(activity, pioneers[position], Toast.LENGTH_SHORT).show()
        }

        return rootView
    }
}