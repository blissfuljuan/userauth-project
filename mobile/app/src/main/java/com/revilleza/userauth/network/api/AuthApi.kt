package com.revilleza.userauth.network.api

import com.revilleza.userauth.network.dto.AuthResponse
import com.revilleza.userauth.network.dto.LoginRequest
import com.revilleza.userauth.network.dto.RegisterRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("/api/auth/register")
    suspend fun register(@Body req: RegisterRequest): AuthResponse

    @POST("/api/auth/login")
    suspend fun login(@Body req: LoginRequest): AuthResponse
}