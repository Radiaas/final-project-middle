package com.example.finalprojectmiddle

data class LoginResponse(
    val code: Int,
    val status: String,
    val message: String,
    val data: LoginUserData? // Nama diganti agar tidak bentrok dengan register
)

data class LoginUserData(
    val id: Int,
    val name: String,
    val email: String
)
