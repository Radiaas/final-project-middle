package com.example.finalprojectmiddle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class WisataAdapter : RecyclerView.Adapter<WisataAdapter.WisataViewHolder>() {
    private val wisataList = mutableListOf<Wisata>()

    fun submitList(newList: List<Wisata>) {
        wisataList.clear()
        wisataList.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WisataViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_wisata, parent, false)
        return WisataViewHolder(view)
    }

    override fun onBindViewHolder(holder: WisataViewHolder, position: Int) {
        val wisata = wisataList[position]
        holder.bind(wisata)
    }

    override fun getItemCount(): Int = wisataList.size

    class WisataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)
        private val titleView: TextView = itemView.findViewById(R.id.titleView)

        fun bind(wisata: Wisata) {
            titleView.text = wisata.title
            Glide.with(itemView.context).load(wisata.image_url).into(imageView)
        }
    }
}
