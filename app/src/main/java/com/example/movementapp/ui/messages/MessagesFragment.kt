package com.example.movementapp.ui.messages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movementapp.R
import com.example.movementapp.ServiceGenerator
import com.example.movementapp.adapters.MessagesAdapter
import com.example.movementapp.database.entity.ChatEntity
import com.example.movementapp.database.entity.MessageEntity
import com.example.movementapp.interfaces.FTravelerAPI
import com.example.movementapp.interfaces.MyCall
import com.example.movementapp.interfaces.MyCallback
import com.example.movementapp.models.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Response
import java.io.IOException


class MessagesFragment : Fragment() {

    companion object {
        fun newInstance() = MessagesFragment()
    }

    private lateinit var viewModel: MessagesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.nav_view)
        navBar.visibility = View.GONE
        val root = inflater.inflate(R.layout.fragment_chat, container, false)
        val listView: RecyclerView = root.findViewById<View>(R.id.messages_view) as RecyclerView
        val voiceRecordingOrSend: FloatingActionButton = root.findViewById(R.id.voiceRecordingOrSend)
        val message: EditText = root.findViewById(R.id.messageInput)

        val chat_id = requireArguments().getString("chat_id")
        var listItems = arrayListOf<MessageEntity>()
        val adapter = MessagesAdapter(listItems)
        listView.adapter = adapter
        listView.layoutManager = LinearLayoutManager(requireContext())
        (listView.layoutManager as LinearLayoutManager).stackFromEnd = true
        listView.setHasFixedSize(true)

        viewModel =
            ViewModelProviders.of(this, MessagesViewModelFactory(requireActivity().application, chat_id.toString())).get(
                MessagesViewModel::class.java)
        viewModel.getAllChats().observe(viewLifecycleOwner, Observer {
            adapter.setChatMessages(it)
        })


        loadMessages(adapter, chat_id)

        voiceRecordingOrSend.setOnClickListener(View.OnClickListener {
            if (!message.text.toString().isNullOrBlank()){
                val mesasgeSend = MessageSend()
                mesasgeSend.chat_id = chat_id
                mesasgeSend.message = message.text.toString().trim()
                mesasgeSend.random_id = "123456"
                message.text.clear()
                sendMessage(
                    adapter,
                    listItems,
                    mesasgeSend
                )
            }
        })


        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //viewModel = ViewModelProviders.of(this).get(MessagesViewModel::class.java)
        // TODO: Use the ViewModel
    }

    fun sendMessage(adapter: MessagesAdapter, listItems: ArrayList<MessageEntity>, message: MessageSend){
        val API: FTravelerAPI = ServiceGenerator.createService(
            FTravelerAPI::class.java,
            requireContext()
        )
        val call: MyCall<MessageResponse> = API.sendMessage(message)
        call.enqueue(object : MyCallback<MessageResponse> {
            override fun success(response: Response<MessageResponse>?) {
                val msg = response!!.body().result
                activity!!.runOnUiThread {
                    loadMessages(adapter, chat_id = message.chat_id)
                }
            }

            override fun unauthenticated(response: Response<*>?) {
                TODO("Not yet implemented")
            }

            override fun clientError(response: Response<*>?) {
                TODO("Not yet implemented")
            }

            override fun serverError(response: Response<*>?) {
                TODO("Not yet implemented")
            }

            override fun networkError(e: IOException?) {
                TODO("Not yet implemented")
            }

            override fun unexpectedError(t: Throwable?) {
                TODO("Not yet implemented")
            }

        })
    }

    fun loadMessages(
        adapter: MessagesAdapter,
        chat_id: String? = null,
        count: Int? = null,
        offset: Int? = null,
        start_message_id: String? = null
    ){

        val API: FTravelerAPI = ServiceGenerator.createService(
            FTravelerAPI::class.java,
            requireContext()
        )
        val call: MyCall<MessagesResponse> = API.getMessages(
            chat_id = chat_id.toString(),
            count = count,
            offset = offset,
            start_message_id = start_message_id
        )
        call.enqueue(object : MyCallback<MessagesResponse> {
            override fun success(response: Response<MessagesResponse>?) {

                val messages = response!!.body().result
                activity!!.runOnUiThread {
                    messages.items.forEach { message ->
                        message.chat = ChatEntity(_id = chat_id.toString(), name = "")
                        viewModel.insert(message)
                    }
                    //listItems.sortBy{ numberToLong(it.created_at!!) }
                    //adapter.notifyDataSetChanged()
                }
            }

            override fun unauthenticated(response: Response<*>?) {
                TODO("Not yet implemented")
            }

            override fun clientError(response: Response<*>?) {
                TODO("Not yet implemented")
            }

            override fun serverError(response: Response<*>?) {
                TODO("Not yet implemented")
            }

            override fun networkError(e: IOException?) {
                TODO("Not yet implemented")
            }

            override fun unexpectedError(t: Throwable?) {
                println(t?.message.toString())
            }

        })
    }

}
