package com.example.finalprojectmiddle

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class WisataActivity : AppCompatActivity() {

    private val wisataViewModel: WisataViewModel by viewModels()
    private lateinit var wisataAdapter: WisataAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wisata)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        wisataAdapter = WisataAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = wisataAdapter

        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("TOKEN", "") ?: ""

        Log.d("WisataActivity", "Token yang digunakan: $token")

        if (token.isNotEmpty()) {
            wisataViewModel.fetchWisata(token)
        } else {
            Log.e("WisataActivity", "Token tidak ditemukan! Pastikan login berhasil.")
        }

        wisataViewModel.wisataList.observe(this) { list ->
            Log.d("WisataActivity", "Jumlah Data Wisata: ${list.size}")
            wisataAdapter.submitList(list)
        }

        wisataViewModel.errorMessage.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }
}
