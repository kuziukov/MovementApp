package com.example.movementapp.ui.login

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.movementapp.LoginActivity
import com.example.movementapp.R
import com.example.movementapp.ServiceGenerator
import com.example.movementapp.interfaces.FTravelerAPI
import com.example.movementapp.SplashActivity
import com.example.movementapp.models.ResponseAPI
import com.example.movementapp.models.User
import com.example.movementapp.interfaces.MyCall
import com.example.movementapp.interfaces.MyCallback
import com.example.movementapp.utils.TokenController
import com.google.android.material.snackbar.Snackbar
import de.hdodenhof.circleimageview.CircleImageView
import retrofit2.Response
import java.io.IOException


class Step3Fragment : Fragment() {

    val GALLERY_REQUEST = 1001
    private lateinit var homeViewModel: Step3ViewModel
    private lateinit var phofile_avatar: CircleImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        homeViewModel = ViewModelProviders.of(this).get(Step3ViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_login_step_3, container, false)

        val first_name: TextView = root.findViewById(R.id.first_name)
        val second_name: TextView = root.findViewById(R.id.second_name)
        val login_button_step_3: Button = root.findViewById(R.id.login_button_step_3)

        (activity as LoginActivity?)!!.changeToolBarTitle(R.string.title_login_step_3)
        phofile_avatar = root.findViewById<CircleImageView>(R.id.profile_avatar)
        phofile_avatar.setOnClickListener(clickListener)
        login_button_step_3.setOnClickListener(View.OnClickListener {

            val user = User()
            val tokenController = TokenController(requireContext())
            user.name = first_name.text.toString()
            user.surname = second_name.text.toString()

            val API: FTravelerAPI = ServiceGenerator.createService(FTravelerAPI::class.java, requireContext())
            val call: MyCall<ResponseAPI> = API.changeUser(user,
                tokenController.getAccessToken()
            )
            call.enqueue(object : MyCallback<ResponseAPI> {
                override fun success(response: Response<ResponseAPI>?) {

                    val intent = Intent(requireContext(), SplashActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }

                override fun clientError(response: Response<*>?) {

                    println(response?.errorBody()?.string())
                    if (response?.code() == 400) {
                        activity!!.runOnUiThread {
                            Snackbar.make(requireView(), "Profile information incorrect", Snackbar.LENGTH_SHORT).show()
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
        })

        return root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == GALLERY_REQUEST){
            phofile_avatar.setImageURI(data?.data)
        }
    }

    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_REQUEST)
    }

    private val clickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.profile_avatar -> {
                val i = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                try {
                    i.putExtra("return-data", true)
                    startActivityForResult(Intent.createChooser(i, "Select Picture"), GALLERY_REQUEST)
                } catch (ex: ActivityNotFoundException) {
                    ex.printStackTrace()
                }
            }
        }
    }

}
