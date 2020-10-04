package com.example.movementapp.ui.login

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.movementapp.LoginActivity
import com.example.movementapp.R
import com.example.movementapp.RideeAPI
import com.example.movementapp.SplashActivity
import com.example.movementapp.adapters.CodeVerification
import com.example.movementapp.adapters.ResponseAPI
import com.example.movementapp.adapters.Token
import com.example.movementapp.adapters.User
import com.example.movementapp.controller.CodeVerificationController
import com.example.movementapp.controller.UserController
import com.google.gson.Gson


class Step2Fragment : Fragment() {

    private lateinit var homeViewModel: Step2ViewModel
    private lateinit var navController: NavController
    val privateStore = "privateStore"
    private lateinit var store: SharedPreferences.Editor

    @SuppressLint("CommitPrefEdits")
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProviders.of(this).get(Step2ViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_login_step_2, container, false)
        val code: TextView = root.findViewById(R.id.code_step_2)
        val response = requireArguments().getString("response")
        val codeVerification: CodeVerification = Gson().fromJson(response, CodeVerification::class.java)
        (activity as LoginActivity?)!!.changeToolBarTitle(R.string.title_login_step_2)
        navController = Navigation.findNavController(context as Activity, R.id.nav_login_fragment)
        store = requireActivity().getSharedPreferences(privateStore, MODE_PRIVATE).edit()


        code.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                if (s.length >= 6){
                    codeVerification.sms_code = s.toString()
                    val codeVerificationController = CodeVerificationController(registerUserCallBack)
                    codeVerificationController.start(codeVerification)
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
            }
        })
        return root
    }

    var registerUserCallBack: RideeAPI.APICallBack = object : RideeAPI.APICallBack {

        override fun onResponse(responseAPI: ResponseAPI?) {
            println(responseAPI?.result.toString())
            val token: Token = Gson().fromJson(responseAPI?.result.toString(), Token::class.java)
            store.putString("access_token", token.access_token.toString())
            store.putString("expires_in", token.expires_in.toString())
            store.commit()

            val userController = UserController(userCallBack)
            userController.start(token.access_token.toString())
        }

        override fun onFailure(cause: String?) {
            println(cause)
            //Toast.makeText(activity, cause, Toast.LENGTH_LONG).show()
        }
    }

    var userCallBack: RideeAPI.APICallBack = object : RideeAPI.APICallBack {

        override fun onResponse(responseAPI: ResponseAPI?) {
            val user: User = Gson().fromJson(responseAPI?.result.toString(), User::class.java)
            store.putString("user", Gson().toJson(user).toString())
            store.commit()

            if (user.name == null || user.surname == null){
                navController.navigate(R.id.action_navigation_login_step_2_to_navigation_login_step_3)
            }else{
                val intent = Intent(requireContext(), SplashActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }

        }

        override fun onFailure(cause: String?) {
            println(cause)
            //Toast.makeText(activity, cause, Toast.LENGTH_LONG).show()
        }
    }

}
