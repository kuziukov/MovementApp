package com.example.movementapp.ui.login

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.movementapp.LoginActivity
import com.example.movementapp.R
import com.example.movementapp.ServiceGenerator
import com.example.movementapp.SplashActivity
import com.example.movementapp.interfaces.FTravelerAPI
import com.example.movementapp.interfaces.MyCall
import com.example.movementapp.interfaces.MyCallback
import com.example.movementapp.models.CodeVerification
import com.example.movementapp.models.ResponseAPI
import com.example.movementapp.models.Token
import com.example.movementapp.utils.TokenController
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import retrofit2.Response
import java.io.IOException
import java.util.*


class Step2Fragment : Fragment() {

    private lateinit var navController: NavController
    private lateinit var homeViewModel: Step2ViewModel
    var mycounterdown: CountDownTimer? = null
    var codeVerification: CodeVerification = CodeVerification()


    @SuppressLint("CommitPrefEdits", "ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProviders.of(this).get(Step2ViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_login_step_2, container, false)
        val code: TextView = root.findViewById(R.id.code_step_2)
        val status_sms: TextView = root.findViewById(R.id.status_sms)
        val response = requireArguments().getString("response")

        codeVerification = Gson().fromJson(response, CodeVerification::class.java)
        (activity as LoginActivity?)!!.changeToolBarTitle(R.string.title_login_step_2)
        navController = Navigation.findNavController(context as Activity, R.id.nav_login_fragment)

        mycounterdown =
            object : CountDownTimer(codeVerification.expires_in!!.toLong() * 1000, 1000) {
                @SuppressLint("SetTextI18n")
                override fun onTick(mycounterup: Long) {
                    val minutes_up_start = (mycounterup / 1000).toInt() / 60
                    val seconds_up_start = (mycounterup / 1000).toInt() % 60
                    val time_2_up_start_formatted: String = java.lang.String.format(
                        Locale.getDefault(), "%02d:%02d",
                        minutes_up_start,
                        seconds_up_start
                    )
                    codeVerification.expires_in = minutes_up_start * 60 + seconds_up_start
                    status_sms.text = "You will be able to request SMS in: $time_2_up_start_formatted"
                }

                override fun onFinish() {
                    (mycounterdown as CountDownTimer).cancel()
                    navController.popBackStack(R.id.navigation_login_step_1, false)
                }
            }
        (mycounterdown as CountDownTimer).start()

        code.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (s.length >= 6) {

                    codeVerification.code = s.toString()

                    val API: FTravelerAPI = ServiceGenerator.createService(
                        FTravelerAPI::class.java,
                        requireContext()
                    )
                    val call: MyCall<ResponseAPI> = API.codeVerification(codeVerification)
                    call.enqueue(object : MyCallback<ResponseAPI> {
                        override fun success(response: Response<ResponseAPI>?) {

                            val navController = Navigation.findNavController(context as Activity, R.id.nav_login_fragment)
                            val tokenController = TokenController(requireContext())
                            val responseAPI: ResponseAPI = response!!.body()
                            val token: Token = Gson().fromJson(responseAPI.result.toString(), Token::class.java)

                            tokenController.setAccessToken(token.access_token.toString())
                            tokenController.setRefreshToken(token.refresh_token.toString())

                            loadUser(tokenController.getAccessToken())
                        }

                        override fun clientError(response: Response<*>?) {

                            println(response?.errorBody())
                            if (response?.code() == 400) {
                                activity!!.runOnUiThread {
                                    Snackbar.make(requireView(), "SMS verification code is invalid", Snackbar.LENGTH_SHORT).show()
                                }
                            }
                            if (response?.code() == 419) {
                            }
                        }

                        override fun unauthenticated(response: Response<*>?) {}
                        override fun serverError(response: Response<*>?) {}
                        override fun networkError(e: IOException?) {}
                        override fun unexpectedError(t: Throwable?) {}
                    })

                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
        return root
    }

    override fun onDetach() {
        super.onDetach()
        (mycounterdown as CountDownTimer).cancel()
    }

    override fun onResume() {
        super.onResume()
        if(codeVerification.expires_in!!.toInt() <= 0){
            navController.popBackStack(R.id.navigation_login_step_1, false)
        }
    }

    fun loadUser(token: String){
        val API: FTravelerAPI = ServiceGenerator.createService(
            FTravelerAPI::class.java,
            requireContext()
        )
        val call: MyCall<ResponseAPI> = API.getUser()
        call.enqueue(object : MyCallback<ResponseAPI> {
            override fun success(response: Response<ResponseAPI>?) {
                val intent = Intent(requireContext(), SplashActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }

            override fun clientError(response: Response<*>?) {
                navController.navigate(R.id.action_navigation_login_step_2_to_navigation_login_step_3)
            }

            override fun unauthenticated(response: Response<*>?) {}
            override fun serverError(response: Response<*>?) {}
            override fun networkError(e: IOException?) {}
            override fun unexpectedError(t: Throwable?) {}
        })
    }

}
