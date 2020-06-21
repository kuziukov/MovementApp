package com.example.movementapp.ui.login

import android.R.attr.button
import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.movementapp.R


class Step2Fragment : Fragment() {

    private lateinit var homeViewModel: Step2ViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProviders.of(this).get(Step2ViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_login_step_2, container, false)
        //val textView: TextView = root.findViewById(R.id.text_home)
        val navController = Navigation.findNavController(context as Activity, R.id.nav_login_fragment);


        /*homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/

        return root
    }
}
