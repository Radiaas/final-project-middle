package com.example.finalprojectmiddle

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class WisataActivity : AppCompatActivity() {

    private val wisataViewModel: WisataViewModel by viewModels()
    private lateinit var wisataAdapter: WisataAdapter
    private lateinit var searchView: SearchView
    private lateinit var textNoData: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wisata)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        searchView = findViewById(R.id.searchView)
        textNoData = findViewById(R.id.textNoData) // Tambahkan TextView untuk pesan "Data tidak ditemukan"

        wisataAdapter = WisataAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = wisataAdapter

        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("TOKEN", "") ?: ""

        if (token.isNotEmpty()) {
            wisataViewModel.fetchWisata(token)
        } else {
            Log.e("WisataActivity", "Token tidak ditemukan! Pastikan login berhasil.")
        }

        wisataViewModel.wisataList.observe(this) { list ->
            if (list.isEmpty()) {
                textNoData.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
            } else {
                textNoData.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
                wisataAdapter.submitList(list)
            }
        }

        wisataViewModel.errorMessage.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query.isNullOrEmpty()) {
                    wisataViewModel.fetchWisata(token) // Jika pencarian kosong, kembali ke daftar awal
                } else {
                    wisataViewModel.searchWisata(token, query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    wisataViewModel.fetchWisata(token) // Jika input dihapus, kembali ke daftar awal
                }
                return false
            }
        })
    }
}
