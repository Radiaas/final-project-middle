package com.example.finalprojectmiddle

data class ApiResponse(
    val code: Int,
    val status: String,
    val message: String,
    val data: WisataData
)

data class WisataData(
    val wisatalist: List<Wisata>
)

data class Wisata(
    val id: Int,
    val image_url: String,
    val title: String
)
