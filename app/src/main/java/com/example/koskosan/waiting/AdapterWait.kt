package com.example.koskosan.waiting

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
import com.example.koskosan.model.UserCheckout
import com.google.firebase.auth.FirebaseAuth

class AdapterWait(private var dataList: ArrayList<UserCheckout>,
                  private var listnener: (UserCheckout) -> Unit):RecyclerView.Adapter<AdapterWait.ViewHolder>() {

    lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterWait.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        context = parent.context
        val layoutInlfatedView = layoutInflater.inflate(R.layout.list_item_wait,parent, false)
        return ViewHolder(layoutInlfatedView)
    }



    override fun onBindViewHolder(holder: AdapterWait.ViewHolder, position: Int) {
        holder.bindItem(dataList[position],listnener,context)
    }

    override fun getItemCount(): Int  = dataList.size

    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
        private var imageUser : ImageView = view.findViewById(R.id.ivUser)
        private var nameUser : TextView = view.findViewById(R.id.tvUser)
        private var fasilitas : TextView = view.findViewById(R.id.tvfasiliti)
        private var poster : ImageView = view.findViewById(R.id.ivPosterWait)
        private var kamar : TextView = view.findViewById(R.id.tvKamar)
        private var bulan : TextView = view.findViewById(R.id.tvBulan)
        private var status : TextView = view.findViewById(R.id.tvStatus)
        private var harga : TextView = view.findViewById(R.id.tvHarga)
        private var pending : TextView = view.findViewById(R.id.tvPending)


        fun bindItem(dataList: UserCheckout, listnener: (UserCheckout) -> Unit, context: Context) {
            Glide.with(context)
                .load(dataList.imageUser)
                .placeholder(R.drawable.proggres_animation)
                .into(imageUser)

            nameUser.text = dataList.nameUser
            fasilitas.text = dataList.fasilitas

            Glide.with(context)
                .load(dataList.imagePoster)
                .placeholder(R.drawable.proggres_animation)
                .into(poster)

            kamar.text = dataList.spinerKamar
            bulan.text = dataList.spinerBulan
            status.text = dataList.status
            harga.formatPrice(dataList.price)
            pending.text = dataList.pending

            itemView.setOnClickListener {
                listnener(dataList)
            }
        }

    }
}