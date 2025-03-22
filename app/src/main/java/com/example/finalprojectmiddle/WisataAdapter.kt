package com.example.finalprojectmiddle

import android.content.Intent
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
        val likeIcon: ImageView = view.findViewById(R.id.likeIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WisataViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_wisata, parent, false)
        return WisataViewHolder(view)
    }

    override fun onBindViewHolder(holder: WisataViewHolder, position: Int) {
        val wisata = wisataList[position]

        // Log ID wisata
        Log.d("WisataAdapter", "ID Wisata: ${wisata.id}, Title: ${wisata.title}")

        holder.titleView.text = wisata.title
        holder.lokasiView.text = wisata.lokasi

        // Load gambar dari API menggunakan Glide
        Glide.with(holder.imageView.context)
            .load(convertGoogleDriveUrl(wisata.image_url))
            .into(holder.imageView)

        if (wisata.liked) {
            holder.likeIcon.setImageResource(R.drawable.ic_heart_liked_foreground)
        } else {
            holder.likeIcon.setImageResource(R.drawable.ic_heart_unliked_foreground)
        }

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetailWisata::class.java).apply {
                putExtra("ID_WISATA", wisata.id)
                putExtra("IMAGE_URL", convertGoogleDriveUrl(wisata.image_url))
                putExtra("TITLE", wisata.title)
                putExtra("LOKASI", wisata.lokasi)
                putExtra("DESKRIPSI", wisata.deskripsi)
                putExtra("LIKED", wisata.liked)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = wisataList.size

    private fun convertGoogleDriveUrl(originalUrl: String): String {
        val regex = "https://drive.google.com/file/d/(.*?)/view".toRegex()
        val matchResult = regex.find(originalUrl)
        return if (matchResult != null) {
            val fileId = matchResult.groupValues[1]
            "https://drive.google.com/uc?export=view&id=$fileId"
        } else {
            originalUrl
        }
    }
}
