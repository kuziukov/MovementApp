package com.example.movementapp.ui.login

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.movementapp.LoginActivity
import com.example.movementapp.R
import com.example.movementapp.RideeAPI
import com.example.movementapp.SplashActivity
import com.example.movementapp.adapters.ResponseAPI
import com.example.movementapp.adapters.User
import com.example.movementapp.controller.UserPostController
import com.google.gson.Gson
import de.hdodenhof.circleimageview.CircleImageView


class Step3Fragment : Fragment() {

    private lateinit var homeViewModel: Step3ViewModel
    private lateinit var phofile_avatar: CircleImageView
    val GALLERY_REQUEST = 1001
    val privateStore = "privateStore"
    private lateinit var store: SharedPreferences

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProviders.of(this).get(Step3ViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_login_step_3, container, false)
        val first_name: TextView = root.findViewById(R.id.first_name)
        val second_name: TextView = root.findViewById(R.id.second_name)
        store = requireActivity().getSharedPreferences(privateStore, Context.MODE_PRIVATE)

        val login_button_step_3: Button = root.findViewById(R.id.login_button_step_3)
        val navController = Navigation.findNavController(context as Activity, R.id.nav_login_fragment)
        phofile_avatar = root.findViewById<CircleImageView>(R.id.profile_avatar)

        (activity as LoginActivity?)!!.changeToolBarTitle(R.string.title_login_step_3)
        phofile_avatar.setOnClickListener(clickListener)
        login_button_step_3.setOnClickListener(View.OnClickListener {
            val user = User()
            user.name = first_name.text.toString()
            user.surname = second_name.text.toString()
            val userPostController = UserPostController(userCallBack)
            println("access_token " + store.getString("access_token", "").toString())
            userPostController.start(user, store.getString("access_token", "").toString())
        })

        return root
    }

    var userCallBack: RideeAPI.APICallBack = object : RideeAPI.APICallBack {

        override fun onResponse(responseAPI: ResponseAPI?) {
            val user: User = Gson().fromJson(responseAPI?.result.toString(), User::class.java)
            val editStore: SharedPreferences.Editor = requireActivity().getSharedPreferences(privateStore, Context.MODE_PRIVATE).edit()
            editStore.putString("user", Gson().toJson(user).toString())
            editStore.commit()

            val intent = Intent(requireContext(), SplashActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        override fun onFailure(cause: String?) {
            println(cause)
            //Toast.makeText(activity, cause, Toast.LENGTH_LONG).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == GALLERY_REQUEST){
            phofile_avatar.setImageURI(data?.data)
        }
    }

    private fun openGalleryForImage() {
        val intent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_REQUEST)
    }

    private val clickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.profile_avatar -> {
                val i = Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                )
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
