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
    fun getWisataList(
        @Query("endpoint") endpoint: String = "listwisata",
        @Query("token") token: String = "c5d98b11-546f-4f03-b92a-aa4a1deb5c89"
    ): Call<ApiResponse>
}
