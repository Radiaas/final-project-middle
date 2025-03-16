package com.example.finalprojectmiddle

data class RegisterResponse(
    val code: Int,
    val status: String,
    val message: String,
    val data: UserData?
)

data class UserData(
    val token: String,
    val user: User
)

data class User(
    val id: Int,
    val nama_lengkap: String,
    val email: String,
    val nomor_hp: String
)
