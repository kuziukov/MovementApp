package com.example.movementapp.ui.login

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.movementapp.LoginActivity
import com.example.movementapp.R
import com.example.movementapp.RideeAPI
import com.example.movementapp.adapters.PhoneVerification
import com.example.movementapp.adapters.ResponseAPI
import com.example.movementapp.controller.PhoneVerificationController
import com.google.gson.Gson


class Step1Fragment : Fragment() {

    private lateinit var homeViewModel: Step1ViewModel
    private lateinit var phoneVerification: PhoneVerification
    private lateinit var login_button_step_1: Button

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProviders.of(this).get(Step1ViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_login_step_1, container, false)
        val code_step_1: EditText = root.findViewById(R.id.code_step_1)
        val phone_step_1: EditText = root.findViewById(R.id.phone_step_1)
        login_button_step_1 = root.findViewById(R.id.login_button_step_1)

        (activity as LoginActivity?)!!.changeToolBarTitle(R.string.title_login)

        login_button_step_1.setOnClickListener(View.OnClickListener {
            login_button_step_1.setClickable(false)
            val phoneVerificationController = PhoneVerificationController(registerUserCallBack)
            phoneVerification = PhoneVerification()
            phoneVerification.number = "${code_step_1.text}${phone_step_1.text}"
            phoneVerificationController.start(phoneVerification)
        })
        return root
    }

    var registerUserCallBack: RideeAPI.APICallBack = object : RideeAPI.APICallBack {

        override fun onResponse(responseAPI: ResponseAPI?) {
            val navController = Navigation.findNavController(context as Activity, R.id.nav_login_fragment);
            responseAPI?.result?.addProperty("number", phoneVerification.number)
            val bundle = Bundle()
            bundle.putString("response", responseAPI?.result.toString())
            navController.navigate(R.id.action_navigation_login_step_1_to_navigation_login_step_2, bundle)
            login_button_step_1.setClickable(true)
        }

        override fun onFailure(cause: String?) {
            login_button_step_1.setClickable(true)
            println(cause)
            //Toast.makeText(activity, cause, Toast.LENGTH_LONG).show()
        }
    }

}
