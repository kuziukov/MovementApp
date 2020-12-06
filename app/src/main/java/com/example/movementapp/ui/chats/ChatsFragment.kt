package com.example.movementapp.ui.chats

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.movementapp.R
import com.example.movementapp.ServiceGenerator
import com.example.movementapp.adapters.ChannelAdapter
import com.example.movementapp.database.entity.ChatEntity
import com.example.movementapp.interfaces.FTravelerAPI
import com.example.movementapp.interfaces.MyCall
import com.example.movementapp.interfaces.MyCallback
import com.example.movementapp.models.*
import retrofit2.Response
import java.io.IOException
import kotlin.collections.ArrayList


class ChatsFragment : Fragment() {

    private lateinit var viewModel: ChatsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)
        val navController =
            Navigation.findNavController(context as Activity, R.id.nav_host_fragment)
        val listView: ListView = root.findViewById(R.id.list_channels)

        val chatsItems: ArrayList<ChatEntity> = ArrayList<ChatEntity>()
        val adapter: ChannelAdapter = ChannelAdapter(requireContext(), chatsItems)
        listView.adapter = adapter

        viewModel =
            ViewModelProviders.of(this, ChatsViewModelFactory(requireActivity().application)).get(ChatsViewModel::class.java)
        viewModel.getAllChats().observe(viewLifecycleOwner, Observer {
            adapter.setChats(it)
        })


        listView.setOnItemLongClickListener(AdapterView.OnItemLongClickListener { arg0, arg1, pos, arg3 ->
            val dialogChannelFragment = DialogChannelFragment()
            dialogChannelFragment.show(childFragmentManager, "ChannelFragment_tag")
            true
        })

        listView.setOnItemClickListener { parent, view, position, id ->

            if (savedInstanceState == null) {
                val bundle = Bundle()
                bundle.putString("chat_id", adapter.getChat(position)._id)
                navController.navigate(R.id.action_navigation_notifications_to_chatFragment, bundle)
            }
        }

        load_chats()

        return root
    }

    fun load_chats(){
        val API: FTravelerAPI = ServiceGenerator.createService(
            FTravelerAPI::class.java,
            requireContext()
        )
        val call: MyCall<ChatsResponse> = API.getChats()
        call.enqueue(object : MyCallback<ChatsResponse> {
            override fun success(response: Response<ChatsResponse>?) {
                val chats = response!!.body().result
                activity!!.runOnUiThread {
                    chats.items.forEach { chat->
                        viewModel.insert(chat)
                    }
                }

            }

            override fun unauthenticated(response: Response<*>?) {
                TODO("Not yet implemented")
            }

            override fun clientError(response: Response<*>?) {
                println(response?.errorBody()?.string())
            }

            override fun serverError(response: Response<*>?) {
                TODO("Not yet implemented")
            }

            override fun networkError(e: IOException?) {
                println(e?.message.toString())
            }

            override fun unexpectedError(t: Throwable?) {
                println(t?.message.toString())
            }

        })
    }
}
