package com.example.movementapp.ui.login

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.movementapp.LoginActivity
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
        val code: TextView = root.findViewById(R.id.code_step_2)
        val navController = Navigation.findNavController(context as Activity, R.id.nav_login_fragment)

        (activity as LoginActivity?)!!.changeToolBarTitle(R.string.title_login_step_2)


        code.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                if (s.length >= 6){
                    code.text = ""
                    navController.navigate(R.id.action_navigation_login_step_2_to_navigation_login_step_3)
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
            }
        })

        /*homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/

        return root
    }
}
