package com.example.movementapp.ui.login

import android.R.attr
import android.app.Activity
import android.app.Activity.RESULT_CANCELED
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.movementapp.LoginActivity
import com.example.movementapp.R
import com.example.movementapp.SplashActivity
import de.hdodenhof.circleimageview.CircleImageView
import java.io.IOException


class Step3Fragment : Fragment() {

    private lateinit var homeViewModel: Step3ViewModel
    private lateinit var phofile_avatar: CircleImageView
    val GALLERY_REQUEST = 1001

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProviders.of(this).get(Step3ViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_login_step_3, container, false)
        //val textView: TextView = root.findViewById(R.id.text_home)
        //val code: TextView = root.findViewById(R.id.code_step_2)
        val login_button_step_3: Button = root.findViewById(R.id.login_button_step_3)
        val navController = Navigation.findNavController(context as Activity, R.id.nav_login_fragment)
        phofile_avatar = root.findViewById<CircleImageView>(R.id.profile_avatar)

        (activity as LoginActivity?)!!.changeToolBarTitle(R.string.title_login_step_3)
        
        /*homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/


        phofile_avatar.setOnClickListener(clickListener)


        login_button_step_3.setOnClickListener(View.OnClickListener {
            val intent = Intent(requireContext(), SplashActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
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
