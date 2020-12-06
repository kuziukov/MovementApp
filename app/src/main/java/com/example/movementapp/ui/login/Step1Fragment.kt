package com.example.movementapp.ui.login

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.app.Activity
import android.os.Bundle
import android.telephony.PhoneNumberUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.movementapp.LoginActivity
import com.example.movementapp.R
import com.example.movementapp.ServiceGenerator
import com.example.movementapp.interfaces.FTravelerAPI
import com.example.movementapp.interfaces.MyCall
import com.example.movementapp.interfaces.MyCallback
import com.example.movementapp.models.Phone
import com.example.movementapp.models.PhoneVerification
import com.example.movementapp.models.ResponseSMS
import com.example.movementapp.utils.ToastCustom
import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.google.i18n.phonenumbers.Phonenumber
import org.json.JSONObject
import retrofit2.Response
import java.io.IOException


class Step1Fragment : Fragment() {

    private lateinit var homeViewModel: Step1ViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProviders.of(this).get(Step1ViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_login_step_1, container, false)
        val code_step_1: EditText = root.findViewById(R.id.code_step_1)
        val phone_step_1: EditText = root.findViewById(R.id.phone_step_1)
        val login_button_step_1: Button = root.findViewById(R.id.login_button_step_1)
        val customToast = ToastCustom(inflater, root)

        (activity as LoginActivity?)!!.changeToolBarTitle(R.string.title_login)

        login_button_step_1.setOnClickListener(View.OnClickListener {

            val phoneVerification = PhoneVerification()
            phoneVerification.number = "${code_step_1.text}${phone_step_1.text}"

            val phoneUtil: PhoneNumberUtil = PhoneNumberUtil.getInstance()
            try {
                val swissNumberProto: Phonenumber.PhoneNumber = phoneUtil.parse(phoneVerification.number, "None")
            } catch (e: NumberParseException) {
                phone_step_1.error = "Enter phone number"
                return@OnClickListener
            }

            phone_step_1.setError(null)
            val scale: Animator = ObjectAnimator.ofPropertyValuesHolder(it,
                PropertyValuesHolder.ofFloat(View.SCALE_X, 1f, 0.9f, 1f),
                PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f, 0.9f, 1f)
            )
            scale.duration = 200
            scale.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    val API: FTravelerAPI = ServiceGenerator.createService(
                        FTravelerAPI::class.java,
                        requireContext()
                    )
                    val call: MyCall<ResponseSMS> = API.phoneVerification(phoneVerification)
                    call.enqueue(object : MyCallback<ResponseSMS> {
                        override fun success(response: Response<ResponseSMS>?) {
                            val phone: Phone? = response?.body()?.result
                            val bundle = Bundle()
                            val navController = Navigation.findNavController(context as Activity, R.id.nav_login_fragment);

                            val res = JSONObject()
                            res.put("number", phoneVerification.number)
                            res.put("verify_key", phone?.verify_key)
                            res.put("expires_in", phone?.expires_in)
                            bundle.putString("response", res.toString())
                            navController.navigate(R.id.action_navigation_login_step_1_to_navigation_login_step_2, bundle)
                        }

                        override fun unauthenticated(response: Response<*>?) {
                            TODO("Not yet implemented")
                        }

                        override fun clientError(response: Response<*>?) {
                            println(response!!.errorBody().string())
                            activity!!.runOnUiThread {
                                customToast.makeText(requireContext(), "Phone number is invalid").show()
                            }
                        }

                        override fun serverError(response: Response<*>?) {
                            println(response?.errorBody()?.string())
                            activity!!.runOnUiThread {
                                customToast.makeText(requireContext(), "The Service is currently not available.").show()
                            }
                        }

                        override fun networkError(e: IOException?) {
                            activity!!.runOnUiThread {
                                customToast.makeText(requireContext(), "The Service is currently not available.").show()
                            }
                        }

                        override fun unexpectedError(t: Throwable?) {
                            TODO("Not yet implemented")
                        }

                    })


                }
            })
            scale.start()

        })
        return root
    }
}
