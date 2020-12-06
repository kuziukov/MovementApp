package com.example.movementapp.controller

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString


class EchoWebSocketListener() : WebSocketListener() {

    private val NORMAL_CLOSURE_STATUS = 1000

    private val _liveData = MutableLiveData<String>()
    val liveData: LiveData<String> get() = _liveData

    // Overridden methods

    private fun outputData(string: String) {
        _liveData.postValue(string)
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
        super.onOpen(webSocket, response)
        println("Connection opened")
    }

    override fun onMessage(webSocket: WebSocket, text: String){
        super.onMessage(webSocket, text)
        println("Receiving $text")
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        super.onMessage(webSocket, bytes)
        println("Receiving bytes : " + bytes.hex())

    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        println("Connection failed")
        println(response?.body?.string())
        println(response?.message)
        t.printStackTrace()
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosing(webSocket, code, reason)
        println("Connection closed")
        println("$code $reason")
    }

}