package com.example.finalprojectmiddle

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class WisataAdapter : RecyclerView.Adapter<WisataAdapter.WisataViewHolder>() {

    private var wisataList: List<WisataItem> = listOf()

    fun submitList(list: List<WisataItem>) {
        wisataList = list
        notifyDataSetChanged()
    }

    class WisataViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageView)
        val titleView: TextView = view.findViewById(R.id.titleView)
        val lokasiView: TextView = view.findViewById(R.id.lokasiView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WisataViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_wisata, parent, false)
        return WisataViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: WisataViewHolder, position: Int) {
        val wisata = wisataList[position]

        Log.d("WisataAdapter", "Menampilkan: ${wisata.title} - ${wisata.image_url}")

        holder.titleView.text = wisata.title
        holder.lokasiView.text = wisata.lokasi

        // Konversi URL Google Drive sebelum memuat gambar
        val imageUrl = convertGoogleDriveUrl(wisata.image_url)
        Log.d("ConvertedURL", imageUrl)

        Glide.with(holder.imageView.context)
            .load(imageUrl)
            .into(holder.imageView)
    }

    override fun getItemCount(): Int = wisataList.size

    // Fungsi untuk mengubah URL Google Drive menjadi direct link
    private fun convertGoogleDriveUrl(originalUrl: String): String {
        val regex = "https://drive.google.com/file/d/(.*?)/view".toRegex()
        val matchResult = regex.find(originalUrl)
        return if (matchResult != null) {
            val fileId = matchResult.groupValues[1]
            "https://drive.google.com/uc?export=view&id=$fileId"
        } else {
            originalUrl // Jika format tidak cocok, gunakan URL asli
        }
    }
}
