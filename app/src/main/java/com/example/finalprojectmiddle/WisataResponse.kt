package com.example.finalprojectmiddle

import com.google.gson.annotations.SerializedName

data class WisataResponse(
    val status: String,
    val code: Int,
    val message: String,
    val data: WisataData?
)


data class WisataData(
    @SerializedName("wisataList") val wisataList: List<WisataItem>
)

data class WisataItem(
    val id: Int,
    val image_url: String,
    val title: String,
    val lokasi: String, // Tambahkan lokasi
    val deskripsi: String, // Tambahkan deskripsi jika diperlukan
    val liked: Boolean // Tambahkan liked jika ingin menampilkan status like
)
