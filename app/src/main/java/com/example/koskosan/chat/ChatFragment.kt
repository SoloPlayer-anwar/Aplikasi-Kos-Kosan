package com.example.koskosan.chat


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.koskosan.R
import com.example.koskosan.databinding.FragmentChatBinding
import com.example.koskosan.home.ProgressDialog
import com.example.koskosan.model.NotificationData
import com.example.koskosan.model.PushNotification
import com.example.koskosan.model.ChatData
import com.example.koskosan.retrofit
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList



 const val TAG = "ChatFragment"
class ChatFragment : Fragment() {

    companion object {
       private const val TOPIC = "/topics/myTopic"
        @JvmStatic
        fun newInstance() =
            ChatFragment().apply {
                arguments = Bundle().apply {  }
            }
    }

    private lateinit var mDatabaseReference: DatabaseReference
    private lateinit var mAuth: FirebaseAuth
    private var chatData = ArrayList<ChatData>()
    private lateinit var chatFragmentBinding: FragmentChatBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        chatFragmentBinding = FragmentChatBinding.inflate(layoutInflater)
        return (chatFragmentBinding.root)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)
        mAuth = FirebaseAuth.getInstance()
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Komentar")
        chatFragmentBinding.rvChat.layoutManager = LinearLayoutManager(context)

        getChatData()

        Glide.with(this)
            .load(mAuth.currentUser!!.photoUrl)
            .placeholder(R.drawable.proggres_animation)
            .into(chatFragmentBinding.imageUser)

        chatFragmentBinding.btnsend.setOnClickListener {
            mAuth = FirebaseAuth.getInstance()
            val title = mAuth.currentUser!!.displayName.toString()
            PushNotification(
                NotificationData(
                    title
                ),
                TOPIC
            ).also {
                sendNotification(it)
            }
            saveDataKomentar()
            clearData()

        }



    }

    fun clearData() {
        chatFragmentBinding.etKomentar.text.clear()
    }

    private fun getChatData() {
        val show = ProgressDialog(requireActivity())
        show.showLoading()
        mDatabaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                show.dismissLoading()
                chatData.clear()
                for (item in snapshot.children){
                    val dataChat = item.getValue(ChatData::class.java)
                    chatData.add(dataChat!!)
                }

                if (chatData.isNotEmpty()) {
                    chatFragmentBinding.rvChat.adapter = AdapterChat(chatData) {

                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "${error.message}",Toast.LENGTH_LONG).show()
                show.showLoading()
            }

        })
    }

    private fun saveDataKomentar() {
        val pesan = chatFragmentBinding.etKomentar.text
        val imageUser = FirebaseAuth.getInstance().currentUser?.photoUrl
        val nameUser = FirebaseAuth.getInstance().currentUser?.displayName
        var calendar = Calendar.getInstance()
        var waktusemua = DateFormat.getDateInstance(SimpleDateFormat.FULL).format(calendar.time)
        val waktu = waktusemua.also { chatFragmentBinding.waktu.text = it }

        if (pesan.isEmpty()) {
            chatFragmentBinding.etKomentar.error = "Silahkan Masukan Komentar anda $nameUser"
        }

        val komentarUser = mDatabaseReference.push().key
        if(komentarUser != null) {
           val chatData = ChatData(imageUser.toString(),nameUser, waktu,pesan.toString())
            mDatabaseReference.child(komentarUser).setValue(chatData)
                .addOnCompleteListener {

                }
        }

    }

    }

    private fun sendNotification(notification: PushNotification) = CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = retrofit.api.postNotification(notification)
            if (response.isSuccessful) {
                Log.d(TAG, "Response : ${Gson().toJson(response)}")
            }else {
                Log.e(TAG, response.errorBody().toString())
            }
        }catch (e: Exception) {
            Log.e(TAG, e.toString())
        }
    }




