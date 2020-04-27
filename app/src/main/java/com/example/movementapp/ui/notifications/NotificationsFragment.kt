package com.example.movementapp.ui.notifications

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.movementapp.ui.chat.ChatFragment
import com.example.movementapp.R

class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
            ViewModelProviders.of(this).get(NotificationsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)
        val listView: ListView = root.findViewById(R.id.list_channels)
        val navController = Navigation.findNavController(context as Activity, R.id.nav_host_fragment);

        val listItems = arrayListOf<String>()
        listItems.add("First 1")
        listItems.add("First 1")
        listItems.add("First 1")
        listItems.add("First 1")
        listItems.add("First 1")
        listItems.add("First 1")
        listItems.add("First 1")
        listItems.add("First 1")
        listItems.add("First 1")
        listItems.add("First 1")
        listItems.add("First 1")
        listItems.add("First 1")
        listItems.add("First 1")
        listItems.add("First 1")


        val adapter = ChannelAdapter(
            requireContext(),
            listItems
        )
        listView.adapter = adapter

        listView.setOnItemClickListener { parent, view, position, id ->

            if (savedInstanceState == null) {
                Toast.makeText(context, "Position $position", Toast.LENGTH_SHORT).show()
                navController.navigate(R.id.action_navigation_notifications_to_chatFragment)

            }

        }

        notificationsViewModel.text.observe(viewLifecycleOwner, Observer {
            //textView.text = it
        })
        return root
    }
}
