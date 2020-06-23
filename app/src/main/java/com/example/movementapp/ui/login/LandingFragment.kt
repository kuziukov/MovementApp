package com.example.movementapp.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.movementapp.LoginActivity
import com.example.movementapp.MainActivity
import com.example.movementapp.R


class LandingFragment : Fragment() {

    private lateinit var homeViewModel: LandingViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProviders.of(this).get(LandingViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_landing, container, false)
        //val textView: TextView = root.findViewById(R.id.text_home)
        val navController = Navigation.findNavController(context as Activity, R.id.nav_login_fragment);
        val login_button_step_1: Button = root.findViewById(R.id.login_button_step_1)
        val landing_image: ImageView = root.findViewById(R.id.landing_image)

        (activity as LoginActivity?)!!.changeToolBarTitle(R.string.empty)

        /*homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/

        landing_image.setOnClickListener(View.OnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        })

        login_button_step_1.setOnClickListener(View.OnClickListener {
            navController.navigate(R.id.action_navigation_landing_to_navigation_login_step_1)
        })

        return root
    }

}
