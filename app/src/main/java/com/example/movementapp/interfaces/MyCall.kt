package com.example.movementapp.interfaces

import retrofit2.Response

interface MyCall<T> {
    fun cancel()
    fun enqueue(callback: MyCallback<T>?)
    fun execute(): Response<T>
    fun clone(): MyCall<T> // Left as an exercise for the reader...
    // TODO MyResponse<T> execute() throws MyHttpException;
}
