package com.example.koskosan.dashboard


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

class KosPutriAdapter(private var dataPutri: ArrayList<kosPutri>,
                      private var listnerPutri: (kosPutri) -> Unit):RecyclerView.Adapter<KosPutriAdapter.ViewHolder>() {


    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KosPutriAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        context = parent.context
        val layoutinflatedView = layoutInflater.inflate(R.layout.item_list_putri, parent, false)
        return ViewHolder(layoutinflatedView)
    }

    override fun onBindViewHolder(holder: KosPutriAdapter.ViewHolder, position: Int) {
        holder.bindItem(dataPutri[position], listnerPutri, context)
    }

    override fun getItemCount(): Int = dataPutri.size

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private var ivPutri : ImageView = view.findViewById(R.id.ivPutri)
        private var tvLokasiPutri : TextView = view.findViewById(R.id.tvLokasiPutri)
        private var tvHargaPutri : TextView = view.findViewById(R.id.tvHargaPutri)
        private var tvRatingPutri : TextView = view.findViewById(R.id.tvRatingPutri)


        fun bindItem(dataPutri: kosPutri, listnerPutri: (kosPutri) -> Unit, context: Context) {
            tvLokasiPutri.text = dataPutri.tempat
            tvHargaPutri.formatPrice(dataPutri.harga)
            tvRatingPutri.text = dataPutri.rating

            Glide.with(context)
                .load(dataPutri.image)
                .placeholder(R.drawable.proggres_animation)
                .into(ivPutri)

            itemView.setOnClickListener {
                listnerPutri(dataPutri)
            }
        }

    }
}