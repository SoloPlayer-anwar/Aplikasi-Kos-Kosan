package com.example.koskosan.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.koskosan.Helper.formatPrice
import com.example.koskosan.R
import com.example.koskosan.dashboard.checkout.CheckoutActivity
import com.example.koskosan.databinding.ActivityDetailDashboardBinding
import com.example.koskosan.model.kosPutra
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DetailPutraActivity : AppCompatActivity() {
    lateinit var mDatabaseReference: DatabaseReference
    private lateinit var detailDashboardBinding: ActivityDetailDashboardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailDashboardBinding = ActivityDetailDashboardBinding.inflate(layoutInflater)
        setContentView(detailDashboardBinding.root)

        val putra  = intent.getParcelableExtra<kosPutra>("putra")

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("kosputra")
            .child(putra?.bathroom.toString())

        detailDashboardBinding.tvlokasiDetail.text = putra?.tempat
        detailDashboardBinding.tvratingDetail.text = putra?.rating
        detailDashboardBinding.tvDesc.text = putra?.desc
        detailDashboardBinding.tvWifi.text = putra?.wifi
        detailDashboardBinding.tvBathroom.text = putra?.bathroom
        detailDashboardBinding.tvBed.text = putra?.bed
        detailDashboardBinding.tvBreakfast.text = putra?.breakfast
        detailDashboardBinding.tvhargaDetail.formatPrice(putra?.harga)
        detailDashboardBinding.tvKos.text = putra?.putra


        Glide.with(applicationContext)
            .load(putra?.image)
            .placeholder(R.drawable.proggres_animation)
            .into(detailDashboardBinding.ivposterDetail)

        Glide.with(applicationContext)
            .load(putra?.gambar1)
            .placeholder(R.drawable.proggres_animation)
            .into(detailDashboardBinding.gambar1)

        Glide.with(applicationContext)
            .load(putra?.gambar2)
            .placeholder(R.drawable.proggres_animation)
            .into(detailDashboardBinding.gambar2)

        Glide.with(applicationContext)
            .load(putra?.gambar3)
            .placeholder(R.drawable.proggres_animation)
            .into(detailDashboardBinding.gambar3)

        detailDashboardBinding.back.setOnClickListener {
            finish()
        }

        detailDashboardBinding.btnBooking.setOnClickListener {
            val intent = Intent(this, CheckoutActivity::class.java)
                .putExtra("PutraDetail",putra)
            startActivity(intent)
            finish()
        }

    }
}