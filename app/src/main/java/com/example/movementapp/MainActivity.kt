package com.example.movementapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.movementapp.controller.EchoWebSocketListener
import com.example.movementapp.interfaces.FTravelerAPI
import com.example.movementapp.interfaces.MyCall
import com.example.movementapp.interfaces.MyCallback
import com.example.movementapp.models.Phone
import com.example.movementapp.models.ResponseSMS
import com.example.movementapp.models.StreamResponse
import com.example.movementapp.utils.TokenController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import retrofit2.Response
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private var currentNavController: LiveData<NavController>? = null
    public var httpClient: OkHttpClient? = null
    private lateinit var websocket_url: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.elevation = 0f

        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        } // Else, need to wait for onRestoreInstanceState

        println(TokenController(applicationContext).getAccessToken())

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        // Now that BottomNavigationBar has restored its instance state
        // and its selectedItemId, we can proceed with setting up the
        // BottomNavigationBar with Navigation
        setupBottomNavigationBar()
    }

    /**
     * Called on first creation and when restoring state.
     */
    private fun setupBottomNavigationBar() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.nav_view)

        val navGraphIds = listOf(R.navigation.home, R.navigation.map, R.navigation.messages)

        // Setup the bottom navigation view with a list of navigation graphs
        val controller = bottomNavigationView.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_fragment,
            intent = intent
        )

        // Whenever the selected controller changes, setup the action bar.
        controller.observe(this, Observer { navController ->
            setupActionBarWithNavController(navController)

            navController.addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.fragment_chat -> bottomNavigationView?.visibility = View.GONE
                    else -> bottomNavigationView?.visibility = View.VISIBLE
                }
            }

        })
        currentNavController = controller

        getStream()

    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }

    companion object {
        const val LOCATION_SETTING_REQUEST = 999
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        for (fragment in supportFragmentManager.fragments) {
            fragment.onActivityResult(requestCode, resultCode, data)
        }
    }



    fun startChat(): LiveData<String> {
        val listener = EchoWebSocketListener()
        GlobalScope.launch(Dispatchers.IO) {
            httpClient = OkHttpClient()
            val request = Request.Builder()
                .url(websocket_url)
                .build()


            val webSocket = httpClient!!.newWebSocket(request, listener)
            //webSocket.
            httpClient!!.dispatcher.executorService.shutdown()
        }
        return listener.liveData
    }

    fun getStream(){
        val API: FTravelerAPI = ServiceGenerator.createService(
            FTravelerAPI::class.java,
            applicationContext
        )
        val call: MyCall<StreamResponse> = API.getStream()
        call.enqueue(object : MyCallback<StreamResponse> {
            override fun success(response: Response<StreamResponse>?) {
                val stream = response?.body()?.result
                websocket_url = "wss://api.ftraveler.com/${stream?.key}"
                println(websocket_url)
                startChat()
            }

            override fun unauthenticated(response: Response<*>?) {
                TODO("Not yet implemented")
            }

            override fun clientError(response: Response<*>?) {
                println(response!!.errorBody().string())
            }

            override fun serverError(response: Response<*>?) {
                println(response?.errorBody()?.string())
            }

            override fun networkError(e: IOException?) {
                TODO("Not yet implemented")
            }

            override fun unexpectedError(t: Throwable?) {
                TODO("Not yet implemented")
            }

        })
    }

}
