package com.example.movementapp.ui.login

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.app.Activity
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.movementapp.LoginActivity
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
        val navController = Navigation.findNavController(context as Activity, R.id.nav_login_fragment);
        val login_button_step_1: Button = root.findViewById(R.id.login_button_step_1)
        val privacy_info: TextView = root.findViewById(R.id.privacy_info)
        privacy_info.movementMethod = LinkMovementMethod.getInstance()

        (activity as LoginActivity?)!!.changeToolBarTitle(R.string.empty)

        login_button_step_1.setOnClickListener(View.OnClickListener {

            val scale: Animator = ObjectAnimator.ofPropertyValuesHolder(it,
                PropertyValuesHolder.ofFloat(View.SCALE_X, 1f, 0.9f, 1f),
                PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f, 0.9f, 1f)
            )
            scale.duration = 200
            scale.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    navController.navigate(R.id.action_navigation_landing_to_navigation_login_step_1)
                }
            })
            scale.start()
        })

        return root
    }

}
