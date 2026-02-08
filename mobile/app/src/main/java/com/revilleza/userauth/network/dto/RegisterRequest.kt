package com.revilleza.userauth.network.dto

data class RegisterRequest(
    val firstname: String,
    val middlename: String,
    val lastname: String,
    val email: String,
    val password: String
)
