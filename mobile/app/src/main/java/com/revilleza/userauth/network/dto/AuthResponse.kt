package com.revilleza.userauth.network.dto

data class AuthResponse(
    val token: String? = null,
    val accessToken: String? = null
) {
    fun resolvedToken(): String? = token ?: accessToken
}
