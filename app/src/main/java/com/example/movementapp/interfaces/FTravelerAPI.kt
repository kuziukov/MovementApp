package com.example.movementapp.interfaces

import com.example.movementapp.interfaces.MyCall
import com.example.movementapp.models.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface FTravelerAPI {

    @POST("/v1/oauth/sms")
    fun phoneVerification(@Body number: PhoneVerification): MyCall<ResponseSMS>

    @POST("/v1/oauth/code")
    fun codeVerification(@Body codeVerification: CodeVerification): MyCall<ResponseAPI>

    @GET("/v1/user")
    fun getUser(): MyCall<ResponseAPI>

    @GET("/v1/chats")
    fun getChats(): MyCall<ChatsResponse>

    @GET("/v1/stream")
    fun getStream(): MyCall<StreamResponse>

    @GET("/v1/chat/{chat_id}/messages")
    fun getMessages(
        @Path("chat_id") chat_id: String,
        @Query("count") count : Int? = null,
        @Query("offset") offset: Int? = null,
        @Query("start_message_id") start_message_id: String? = null
    ): MyCall<MessagesResponse>

    @POST("/v1/messages")
    fun sendMessage(@Body message: MessageSend?): MyCall<MessageResponse>

    @POST("/v1/user")
    fun changeUser(@Body user: User?, @Header("Token") access_token: String): MyCall<ResponseAPI>

    @POST("/v1/session")
    fun refreshToken(@Body refresh: RefreshToken): MyCall<ResponseAPI>

    @Multipart
    @POST("/v1/user/upload")
    fun upload(@Part file: MultipartBody.Part)
}