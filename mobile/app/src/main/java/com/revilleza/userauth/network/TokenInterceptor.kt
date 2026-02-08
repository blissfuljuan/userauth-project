package com.revilleza.userauth.network

import android.content.Context
import com.revilleza.userauth.data.TokenManager
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor (
    private val context: Context
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val token = TokenManager.getToken(context)

        val newReq = if(!token.isNullOrBlank()) {
            original.newBuilder()
                .addHeader("Authorization","Bearer $token")
                .build()
        } else {
            original
        }

        return chain.proceed(newReq)
    }

}