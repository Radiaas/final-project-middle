package com.example.finalprojectmiddle

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
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WisataViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_wisata, parent, false)
        return WisataViewHolder(view)
    }

    override fun onBindViewHolder(holder: WisataViewHolder, position: Int) {
        val wisata = wisataList[position]
            Log.d("WisataAdapter", "Menampilkan: ${wisata.title}")
        holder.titleView.text = wisata.title
        Glide.with(holder.imageView.context).load(wisata.image_url).into(holder.imageView)
    }

    override fun getItemCount(): Int = wisataList.size
}
