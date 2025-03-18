package com.example.finalprojectmiddle

data class LoginResponse(
    val code: Int,
    val status: String,
    val message: String,
    val data: LoginData? // Menyesuaikan dengan struktur JSON dari API
)

data class LoginData(
    val token: String,
    val id: Int,
    val nama_lengkap: String,
    val email: String,
    val nomor_hp: String
)
