package com.example.movementapp.ui.login

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.movementapp.R


class Step1Fragment : Fragment() {

    private lateinit var homeViewModel: Step1ViewModel
    private var PERMISSION_ID = 100

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProviders.of(this).get(Step1ViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_login_step_1, container, false)
        //val textView: TextView = root.findViewById(R.id.text_home)
        val navController = Navigation.findNavController(context as Activity, R.id.nav_login_fragment);
        val login_button_step_1: Button = root.findViewById(R.id.login_button_step_1)

        /*homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/

        login_button_step_1.setOnClickListener(View.OnClickListener {
            navController.navigate(R.id.action_navigation_login_step_1_to_navigation_login_step_2)
        })

        return root
    }

}
