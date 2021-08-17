package com.example.koskosan.dashboard

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.koskosan.R
import com.example.koskosan.Helper.formatPrice
import com.example.koskosan.model.kosPutra

class KosPutraAdapter(private var dataPutra: ArrayList<kosPutra>,
                      private var listenerPutra: (kosPutra) -> Unit): RecyclerView.Adapter<KosPutraAdapter.ViewHolder>() {


    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KosPutraAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        context = parent.context
        val layoutinfaltedView = layoutInflater.inflate(R.layout.item_list_putra, parent, false)
        return ViewHolder(layoutinfaltedView)
    }

    override fun onBindViewHolder(holder: KosPutraAdapter.ViewHolder, position: Int) {
        holder.bindItem(dataPutra[position], listenerPutra,context)
    }

    override fun getItemCount(): Int = dataPutra.size

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private var tempat : TextView = view.findViewById(R.id.tvLokasi)
        private var rating : TextView = view.findViewById(R.id.tvRate)
        private var harga : TextView = view.findViewById(R.id.tvHarga)
        private var poster : ImageView = view.findViewById(R.id.ivPoster)

        @SuppressLint("SetTextI18n")
        fun bindItem(dataPutra: kosPutra, listenerPutra: (kosPutra) -> Unit, context: Context) {
            tempat.text = dataPutra.tempat
            rating.text = dataPutra.rating
            harga.formatPrice(dataPutra.harga)


            Glide.with(context)
                .load(dataPutra.image)
                .placeholder(R.drawable.proggres_animation)
                .into(poster)


            itemView.setOnClickListener {
                listenerPutra(dataPutra)
            }
        }


    }



}