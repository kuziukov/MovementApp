package com.example.movementapp.adapters

import com.example.movementapp.interfaces.MyCall
import retrofit2.*
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.util.concurrent.Executor


class ErrorHandlingCallAdapterFactory : CallAdapter.Factory() {
    override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit): CallAdapter<*, *>? {
        if (getRawType(returnType) != MyCall::class.java) {
            return null
        }
        check(returnType is ParameterizedType) { "MyCall must have generic type (e.g., MyCall<ResponseBody>)" }
        val responseType = getParameterUpperBound(0, returnType)
        val callbackExecutor = retrofit.callbackExecutor()
        return ErrorHandlingCallAdapter<Any>(responseType, callbackExecutor)
    }

    private class ErrorHandlingCallAdapter<R> internal constructor(
        private val responseType: Type,
        private val callbackExecutor: Executor
    ) :
        CallAdapter<R, MyCall<R>> {
        override fun responseType(): Type {
            return responseType
        }

        override fun adapt(call: Call<R>): MyCall<R> {
            return MyCallAdapter(call, callbackExecutor)
        }

    }
}
