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
import com.example.koskosan.model.kosPutra

class AdapterSeeMorePutra(private var dataPutra: ArrayList<kosPutra>,
                          private var listenerPutra: (kosPutra) -> Unit):RecyclerView.Adapter<AdapterSeeMorePutra.ViewHolder>() {

    lateinit var context: Context
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterSeeMorePutra.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        context = parent.context
        val layoutinfaltedView = layoutInflater.inflate(R.layout.item_seemore, parent, false)
        return ViewHolder(layoutinfaltedView)
    }



    override fun onBindViewHolder(holder: AdapterSeeMorePutra.ViewHolder, position: Int) {
        holder.bindItem(dataPutra[position], listenerPutra,context)
    }

    override fun getItemCount(): Int = dataPutra.size

    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
        private var lokasi : TextView = view.findViewById(R.id.tvlokasi)
        private var price : TextView = view.findViewById(R.id.tvharga)
        private var rating: TextView = view.findViewById(R.id.tvrating)
        private var poster : ImageView = view.findViewById(R.id.ivposter)


        fun bindItem(dataPutra: kosPutra, listenerPutra: (kosPutra) -> Unit, context: Context) {
            lokasi.text = dataPutra.tempat
            price.formatPrice(dataPutra.harga)
            rating.text = dataPutra.rating

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