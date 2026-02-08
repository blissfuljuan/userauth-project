package com.revilleza.userauth.network

import android.content.Context
import com.revilleza.userauth.network.api.AuthApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.AuthProvider

object ApiClient {

    private const val BASE_URL = "http://10.0.2.2:8080"

    fun createAuthApi(context: Context): AuthApi {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val okHttp = OkHttpClient.Builder()
            .addInterceptor(TokenInterceptor(context))
            .addInterceptor(logging)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttp)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(AuthApi::class.java)
    }
}