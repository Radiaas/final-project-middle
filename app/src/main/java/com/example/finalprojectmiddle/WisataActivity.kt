package com.example.finalprojectmiddle

import android.os.Bundle
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

        wisataViewModel.fetchWisataData("c5d98b11-546f-4f03-b92a-aa4a1deb5c89")

        wisataViewModel.wisataList.observe(this) { wisataList ->
            if (wisataList.isNotEmpty()) {
                wisataAdapter.submitList(wisataList)
            } else {
                Toast.makeText(this, "Gagal mengambil data!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
