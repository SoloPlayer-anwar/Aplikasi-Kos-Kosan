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
import com.example.koskosan.model.UserCheckoutPutri
import com.google.firebase.auth.FirebaseAuth

class AdapterWaitPutri(private var dataPutri: ArrayList<UserCheckoutPutri>,
                       private var listener: (UserCheckoutPutri) -> Unit):RecyclerView.Adapter<AdapterWaitPutri.ViewHolder>() {

    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterWaitPutri.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        context = parent.context
        val layoutInflatedView = layoutInflater.inflate(R.layout.list_item_wait, parent,false)
        return ViewHolder(layoutInflatedView)
    }



    override fun onBindViewHolder(holder: AdapterWaitPutri.ViewHolder, position: Int) {
        holder.bindItem(dataPutri[position],listener,context)
    }

    override fun getItemCount(): Int = dataPutri.size

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val mAuth = FirebaseAuth.getInstance().currentUser
        private var imageUser : ImageView = view.findViewById(R.id.ivUser)
        private var nameUser : TextView = view.findViewById(R.id.tvUser)
        private var fasilitas : TextView = view.findViewById(R.id.tvfasiliti)
        private var poster : ImageView = view.findViewById(R.id.ivPosterWait)
        private var kamar : TextView = view.findViewById(R.id.tvKamar)
        private var bulan : TextView = view.findViewById(R.id.tvBulan)
        private var status : TextView = view.findViewById(R.id.tvStatus)
        private var harga : TextView = view.findViewById(R.id.tvHarga)
        private var pending : TextView = view.findViewById(R.id.tvPending)

        fun bindItem(dataPutri: UserCheckoutPutri, listener: (UserCheckoutPutri) -> Unit, context: Context) {
            Glide.with(context)
                .load(dataPutri.imageUser)
                .placeholder(R.drawable.proggres_animation)
                .into(imageUser)

            nameUser.text = dataPutri.nameUser
            fasilitas.text = dataPutri.fasilitas

            Glide.with(context)
                .load(dataPutri.imagePoster)
                .placeholder(R.drawable.proggres_animation)
                .into(poster)

            kamar.text = dataPutri.spinerKamar
            bulan.text = dataPutri.spinerBulan
            status.text = dataPutri.status
            harga.formatPrice(dataPutri.price)
            pending.text = dataPutri.pending

            itemView.setOnClickListener {
                listener(dataPutri)
            }
        }

    }
}