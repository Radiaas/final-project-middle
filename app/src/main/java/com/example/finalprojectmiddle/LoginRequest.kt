package com.example.finalprojectmiddle

data class LoginRequest(
    val endpoint: String = "login", // Menyertakan endpoint langsung
    val email: String,
    val password: String
)
