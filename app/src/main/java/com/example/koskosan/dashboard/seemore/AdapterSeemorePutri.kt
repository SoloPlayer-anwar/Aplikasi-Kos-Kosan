package com.example.koskosan.dashboard.seemore

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.koskosan.Helper.formatPrice
import com.example.koskosan.R
import com.example.koskosan.model.kosPutri

class AdapterSeemorePutri(private var dataPutri: ArrayList<kosPutri>,
                          private var listinerPutri: (kosPutri) -> Unit): RecyclerView.Adapter<AdapterSeemorePutri.ViewHolder>() {


    lateinit var context: Context

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterSeemorePutri.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        context = parent.context
        val layoutInlfatedView = layoutInflater.inflate(R.layout.item_seemore,parent,false)
        return ViewHolder(layoutInlfatedView)
    }

    override fun onBindViewHolder(holder: AdapterSeemorePutri.ViewHolder, position: Int) {
        holder.bindItem(dataPutri[position], listinerPutri,context)
    }

    override fun getItemCount(): Int = dataPutri.size

    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
        private var lokasi: TextView = view.findViewById(R.id.tvlokasi)
        private var price: TextView = view.findViewById(R.id.tvharga)
        private var rating: TextView = view.findViewById(R.id.tvrating)
        private var poster: ImageView = view.findViewById(R.id.ivposter)


        fun bindItem(dataPutri: kosPutri, listinerPutri: (kosPutri) -> Unit, context: Context) {
            lokasi.text = dataPutri.tempat
            price.formatPrice(dataPutri.harga)
            rating.text = dataPutri.rating


            Glide.with(context)
                .load(dataPutri.image)
                .placeholder(R.drawable.proggres_animation)
                .into(poster)

            itemView.setOnClickListener {
                listinerPutri(dataPutri)
            }
        }

    }
}