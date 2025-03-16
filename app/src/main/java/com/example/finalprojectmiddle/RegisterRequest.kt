package com.example.finalprojectmiddle

data class RegisterRequest(
    val endpoint: String = "register",
    val nama_lengkap: String,
    val email: String,
    val nomor_hp: String,
    val password: String,
    val confirm_password: String
)
