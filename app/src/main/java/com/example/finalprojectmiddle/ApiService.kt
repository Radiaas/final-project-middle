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

    @GET("exec")
    suspend fun searchWisata(
        @Query("endpoint") endpoint: String = "searchWisata",
        @Query("token") token: String,
        @Query("keyword") keyword: String
    ): Response<WisataResponse>

    @POST("exec")
    suspend fun logoutUser(
        @Query("endpoint") endpoint: String = "logout",
        @Query("token") token: String
    ): Response<LogoutResponse>

    @GET("exec")
    fun getProfile(
        @Query("endpoint") endpoint: String = "profile",
        @Query("token") token: String
    ): Call<ProfileResponse>

    @POST("exec")
    fun likeWisata(
        @Query("endpoint") endpoint: String = "likeWisata",
        @Query("token") token: String,
        @Query("id_wisata") idWisata: Int
    ): Call<Void>

    @POST("exec")
    fun unlikeWisata(
        @Query("endpoint") endpoint: String = "unlikeWisata",
        @Query("token") token: String,
        @Query("id_wisata") idWisata: Int
    ): Call<Void>

    @POST("exec")
    fun addBookmark(
        @Query("endpoint") endpoint: String = "addBookmarks",
        @Query("token") token: String,
        @Query("id_wisata") idWisata: Int
    ): Call<Void>

    @POST("exec")
    fun removeBookmark(
        @Query("endpoint") endpoint: String = "removeBookmarks",
        @Query("token") token: String,
        @Query("id_wisata") idWisata: Int
    ): Call<Void>
}
