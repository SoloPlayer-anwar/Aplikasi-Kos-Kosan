package com.example.koskosan.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.koskosan.databinding.ItemSlideBinding
import com.example.koskosan.model.ImageData

class ImageSliderAdapter(private var items: List<ImageData>, ): RecyclerView.Adapter<ImageSliderAdapter.imageViewHolder>() {
    inner class imageViewHolder(itemView: ItemSlideBinding): RecyclerView.ViewHolder(itemView.root) {
        private val binding = itemView
        fun bindItem(data: ImageData) {
            with(binding) {
                Glide.with(itemView)
                    .load(data.imageUrl)
                    .into(ivSlider)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): imageViewHolder {
        return imageViewHolder(ItemSlideBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    }

    override fun onBindViewHolder(holder: imageViewHolder, position: Int) {
        holder.bindItem(items[position])
    }

    override fun getItemCount(): Int = items.size


}