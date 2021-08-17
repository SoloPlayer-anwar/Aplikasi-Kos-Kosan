package com.example.koskosan.profile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.koskosan.R
import com.example.koskosan.databinding.ActivityProfileBinding
import com.example.koskosan.google.LoginGoogleActivity
import com.google.firebase.auth.FirebaseAuth

class ProfileActivity : AppCompatActivity() {
    private lateinit var profileBinding: ActivityProfileBinding
    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        profileBinding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(profileBinding.root)

        mAuth = FirebaseAuth.getInstance()

        Glide.with(this)
            .load(mAuth.currentUser?.photoUrl)
            .placeholder(R.drawable.proggres_animation)
            .into(profileBinding.ivProfile)

        profileBinding.ivNama.text = mAuth.currentUser?.displayName
        profileBinding.tvEmail.text = mAuth.currentUser?.email

        profileBinding.btnLogout.setOnClickListener {
            mAuth.signOut()
            val intent = Intent(this, LoginGoogleActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}