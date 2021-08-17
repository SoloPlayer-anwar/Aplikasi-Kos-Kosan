package com.example.koskosan.chat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.koskosan.R
import com.example.koskosan.model.ChatData
import com.example.koskosan.model.NotificationData
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class AdapterChat(private var dataChat: ArrayList<ChatData>,
                  private var listenerChat: (ChatData) -> Unit):RecyclerView.Adapter<AdapterChat.ViewHolder>() {


    lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterChat.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        context = parent.context
        val layoutInfaltedView = layoutInflater.inflate(R.layout.list_item_chat, parent,false)
        return ViewHolder(layoutInfaltedView)
    }

    override fun onBindViewHolder(holder: AdapterChat.ViewHolder, position: Int) {
        holder.bindItem(dataChat[position],listenerChat,context)
    }

    override fun getItemCount(): Int = dataChat.size

    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
        private var imageUser: ImageView = view.findViewById(R.id.imageUser)
        private var title : TextView = view.findViewById(R.id.title)
        private var komentar : TextView = view.findViewById(R.id.row_item_komentar)
        private var waktu : TextView = view.findViewById(R.id.waktu)




        fun bindItem(dataChat: ChatData, listenerChat: (ChatData) -> Unit, context: Context) {
            Glide.with(context)
                .load(dataChat.imageUser)
                .placeholder(R.drawable.proggres_animation)
                .into(imageUser)

            title.text = dataChat.nameUser
            komentar.text = dataChat.pesan
            waktu.text = dataChat.waktu

            itemView.setOnClickListener {
                listenerChat(dataChat)
            }

        }


    }
}