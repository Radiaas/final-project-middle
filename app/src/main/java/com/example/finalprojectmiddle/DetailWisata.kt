package com.example.finalprojectmiddle

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailWisata : AppCompatActivity() {
    private var isLiked: Boolean = false
    private var isBookmarked: Boolean = false
    private lateinit var token: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_wisata)

        // Ambil token dari SharedPreferences
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        token = sharedPreferences.getString("TOKEN", null) ?: run {
            Toast.makeText(this, "Token not found", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Ambil data dari Intent
        val imageUrl = intent.getStringExtra("IMAGE_URL")
        val title = intent.getStringExtra("TITLE")
        val lokasi = intent.getStringExtra("LOKASI")
        val deskripsi = intent.getStringExtra("DESKRIPSI")
        val idWisata = intent.getIntExtra("ID_WISATA", 0)
        isLiked = intent.getBooleanExtra("LIKED", false)

        // Inisialisasi view
        val imageView = findViewById<ImageView>(R.id.detailImageView)
        val titleView = findViewById<TextView>(R.id.detailTitleView)
        val lokasiView = findViewById<TextView>(R.id.detailLokasiView)
        val deskripsiView = findViewById<TextView>(R.id.detailDeskripsiView)
        val likeIcon = findViewById<ImageView>(R.id.likeIconDetail)
        val bookmarkIcon = findViewById<ImageView>(R.id.bookmarkIconDetail)

        // Tampilkan data ke view
        Glide.with(this).load(imageUrl).into(imageView)
        titleView.text = title
        lokasiView.text = lokasi
        deskripsiView.text = deskripsi

        // Update icon awal
        updateLikeIcon(likeIcon)
        updateBookmarkIcon(bookmarkIcon)

        // Listener untuk like
        likeIcon.setOnClickListener {
            if (isLiked) {
                unlikeWisata(idWisata, likeIcon)
            } else {
                likeWisata(idWisata, likeIcon)
            }
        }

        // Listener untuk bookmark
        bookmarkIcon.setOnClickListener {
            if (isBookmarked) {
                removeBookmark(idWisata, bookmarkIcon)
            } else {
                addBookmark(idWisata, bookmarkIcon)
            }
        }
    }

    private fun addBookmark(idWisata: Int, bookmarkIcon: ImageView) {
        val apiService = RetrofitClient.instance
        apiService.addBookmark(token = token, idWisata = idWisata)
            .enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        isBookmarked = true
                        updateBookmarkIcon(bookmarkIcon)
                        Toast.makeText(this@DetailWisata, "Bookmark added!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@DetailWisata, "Failed to add bookmark.", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(this@DetailWisata, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun removeBookmark(idWisata: Int, bookmarkIcon: ImageView) {
        val apiService = RetrofitClient.instance
        apiService.removeBookmark(token = token, idWisata = idWisata)
            .enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        isBookmarked = false
                        updateBookmarkIcon(bookmarkIcon)
                        Toast.makeText(this@DetailWisata, "Bookmark removed!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@DetailWisata, "Failed to remove bookmark.", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(this@DetailWisata, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun likeWisata(idWisata: Int, likeIcon: ImageView) {
        val apiService = RetrofitClient.instance
        apiService.likeWisata(token = token, idWisata = idWisata)
            .enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        isLiked = true
                        updateLikeIcon(likeIcon)
                        Toast.makeText(this@DetailWisata, "Liked!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@DetailWisata, "Failed to like", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(this@DetailWisata, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun unlikeWisata(idWisata: Int, likeIcon: ImageView) {
        val apiService = RetrofitClient.instance
        apiService.unlikeWisata(token = token, idWisata = idWisata)
            .enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        isLiked = false
                        updateLikeIcon(likeIcon)
                        Toast.makeText(this@DetailWisata, "Unliked!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@DetailWisata, "Failed to unlike", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(this@DetailWisata, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun updateLikeIcon(likeIcon: ImageView) {
        likeIcon.setImageResource(
            if (isLiked) R.drawable.ic_heart_liked_foreground else R.drawable.ic_heart_unliked_foreground
        )
    }

    private fun updateBookmarkIcon(bookmarkIcon: ImageView) {
        bookmarkIcon.setImageResource(
            if (isBookmarked) R.drawable.ic_bookmark_selected
            else R.drawable.ic_bookmark_unselected
        )
    }
}
