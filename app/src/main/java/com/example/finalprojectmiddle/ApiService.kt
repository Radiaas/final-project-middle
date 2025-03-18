package com.example.finalprojectmiddle

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("exec")
    fun registerUser(@Body request: RegisterRequest): Call<RegisterResponse>

    @Headers("Content-Type: application/json")
    @POST("exec")
    suspend fun loginUser(@Body request: LoginRequest): Response<LoginResponse>

    @GET("exec")
    suspend fun getWisata(
        @Query("endpoint") endpoint: String = "listwisata",
        @Query("token") token: String
    ): Response<WisataResponse>
}
