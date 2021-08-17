package com.example.koskosan.dashboard.checkout

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.koskosan.Helper.formatPrice
import com.example.koskosan.R
import com.example.koskosan.databinding.ActivityPilihKamarBinding
import com.example.koskosan.model.UserCheckout
import com.example.koskosan.model.kosPutra
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CheckoutActivity : AppCompatActivity() {

    private lateinit var refData : DatabaseReference
    private lateinit var pilihKamarBinding: ActivityPilihKamarBinding

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pilihKamarBinding = ActivityPilihKamarBinding.inflate(layoutInflater)
        setContentView(pilihKamarBinding.root)

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_PHONE_STATE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_PHONE_STATE),
                101
            )
        }


        pilihKamarBinding.btnCheckout.setOnClickListener {
            saveData()
        }

        refData = FirebaseDatabase.getInstance().getReference("Checkout")
        val putra = intent.getParcelableExtra<kosPutra>("PutraDetail")


        Glide.with(this)
            .load(putra?.image)
            .placeholder(R.drawable.proggres_animation)
            .into(pilihKamarBinding.ivPosterCheckout)

        pilihKamarBinding.tvlokasiCheckout.text = putra?.tempat
        pilihKamarBinding.tvrateCheckout.text = putra?.rating
        pilihKamarBinding.tvPrice.formatPrice(putra?.harga)

        val personeName = arrayOf("A3", "A4", "C3", "C4", "D3", "E3")
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, personeName)

        val personBulan = arrayOf("1 Bulan")
        val arrayBulan = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, personBulan)

        val status = arrayOf("Mahasiswa","Pekerja","Freelance","Menikah")
        val arrayStatus = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,status)



        pilihKamarBinding.spKamar.adapter = arrayAdapter
        pilihKamarBinding.spKamar.onItemSelectedListener = object :

            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                pilihKamarBinding.spinerResult.text= personeName[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(applicationContext, "Silahkan pilih kamar anda",Toast.LENGTH_SHORT).show()
            }

        }

        pilihKamarBinding.spBulan.adapter = arrayBulan
        pilihKamarBinding.spBulan.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                pilihKamarBinding.spinerResultBulan.text = personBulan[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

        pilihKamarBinding.spStatus.adapter = arrayStatus
        pilihKamarBinding.spStatus.onItemSelectedListener = object :


            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                pilihKamarBinding.tvKelasResult.text = status[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(applicationContext, "Silahkan isi identitas", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun saveData() {
        val putra = intent.getParcelableExtra<kosPutra>("PutraDetail")
        val formId = FirebaseAuth.getInstance().currentUser?.uid.toString()
        val imageUser = FirebaseAuth.getInstance().currentUser?.photoUrl.toString()
        val nameUser = FirebaseAuth.getInstance().currentUser?.displayName.toString()
        val spinerKamar = pilihKamarBinding.spinerResult.text.toString()
        val spinerBulan = pilihKamarBinding.spinerResultBulan.text.toString()
        val imagePoster = putra?.image.toString()
        val price = putra?.harga
        val rateCheck = putra?.rating.toString()
        val status = pilihKamarBinding.tvKelasResult.text.toString()
        val fasilitas = pilihKamarBinding.tvFasilitas.text.toString()
        val pending = pilihKamarBinding.pending.text.toString()




        val userId = refData.push().key
        refData.setValue("Free",fasilitas)
        refData.setValue("Pending", pending)
            val userCheckout = UserCheckout(spinerKamar,spinerBulan,imagePoster,formId,nameUser,imageUser,price,rateCheck, status,fasilitas,pending)
            refData.child(userId.toString()).setValue(userCheckout).addOnCompleteListener {
                val intent = Intent(this, PaymentPutraActivity::class.java)
                    .putExtra("putra", putra)
                startActivity(intent)
            }
    }

}









