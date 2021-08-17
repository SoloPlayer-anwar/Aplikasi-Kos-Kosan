package com.example.koskosan.dashboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.koskosan.Helper.formatPrice
import com.example.koskosan.dashboard.checkout.CheckoutPutriActivity
import com.example.koskosan.databinding.ActivityDetailPutriBinding
import com.example.koskosan.model.kosPutri
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DetailPutriActivity : AppCompatActivity() {
    lateinit var mDatabaseReference: DatabaseReference
    private lateinit var detailPutriBinding: ActivityDetailPutriBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailPutriBinding = ActivityDetailPutriBinding.inflate(layoutInflater)
        setContentView(detailPutriBinding.root)


        val putri = intent.getParcelableExtra<kosPutri>("putri")

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("kosputri")
            .child(putri?.bathroom.toString())

        detailPutriBinding.tvlokasiPutri.text = putri?.tempat
        detailPutriBinding.tvratingPutri.text = putri?.rating
        detailPutriBinding.tvdescPutri.text = putri?.desc
        detailPutriBinding.tvwifiPutri.text = putri?.wifi
        detailPutriBinding.tvbathroomPutri.text = putri?.bathroom
        detailPutriBinding.tvbreakfastPutri.text = putri?.breakfast
        detailPutriBinding.tvbedPutri.text = putri?.bed
        detailPutriBinding.tvhargaPutri.formatPrice(putri?.harga)
        detailPutriBinding.tvkosPutri.text = putri?.putri

        Glide.with(applicationContext)
            .load(putri?.image)
            .into(detailPutriBinding.ivposterPutri)

        Glide.with(applicationContext)
            .load(putri?.gambar1)
            .into(detailPutriBinding.gambar1Putri)

        Glide.with(applicationContext)
            .load(putri?.gambar2)
            .into(detailPutriBinding.gambar2Putri)

        Glide.with(applicationContext)
            .load(putri?.gambar3)
            .into(detailPutriBinding.gambar3Putri)

        detailPutriBinding.backPutri.setOnClickListener {
            finish()
        }

        detailPutriBinding.btnBooking.setOnClickListener {
            val intent = Intent(this, CheckoutPutriActivity::class.java)
                .putExtra("PutriDetail", putri)
            startActivity(intent)
            finish()
        }

    }
}