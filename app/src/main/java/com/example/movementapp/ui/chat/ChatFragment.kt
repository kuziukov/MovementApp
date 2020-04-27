package com.example.movementapp.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.movementapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.view.*


class ChatFragment : Fragment() {

    companion object {
        fun newInstance() = ChatFragment()
    }

    private lateinit var viewModel: ChatViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val navBar: BottomNavigationView = activity!!.findViewById(R.id.nav_view)
        navBar.visibility = View.GONE
        val root = inflater.inflate(R.layout.fragment_chat, container, false)
        val listView: ListView = root.findViewById(R.id.messages_view)

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

        val adapter = ChatAdapter(
            requireContext(),
            listItems
        )
        listView.adapter = adapter


        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ChatViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
