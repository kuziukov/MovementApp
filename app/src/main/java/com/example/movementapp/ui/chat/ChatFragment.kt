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
        listItems.add("Привет))")
        listItems.add("Как дела?")
        listItems.add("Здароу")
        listItems.add("С наступающим)")
        listItems.add("Нормально:B")
        listItems.add("Привет )")
        listItems.add("По наискорейшему спуску что надо было от руки считать не вкурсе?)")
        listItems.add("Я уже не помню")
        listItems.add("Там две итерации надо было считать, обоими методами")
        listItems.add("Кстати, удачи в конкурсе )")
        listItems.add("Спасибо)")
        listItems.add("First 1")
        listItems.add("1234")
        listItems.add("12")
        listItems.add("1")
        listItems.add(")")
        listItems.add("Somehow this layout is not working for me. All I see is a white little round corner rectangle at the top left corner and a bigger grey arrow is seen overlapped. Any idea what am I missing?")
        listItems.add("Somehow this layout is not working for me. All I see is a white little round corner rectangle at the top left corner and a bigger grey arrow is seen overlapped. Any idea what am I missing?")

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
