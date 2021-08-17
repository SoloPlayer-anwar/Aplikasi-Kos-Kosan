package com.example.koskosan.dashboard.checkout

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.koskosan.Helper.formatPrice
import com.example.koskosan.R
import com.example.koskosan.databinding.ActivityCheckoutPutriBinding
import com.example.koskosan.model.UserCheckoutPutri
import com.example.koskosan.model.kosPutri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CheckoutPutriActivity : AppCompatActivity() {
    lateinit var refData: DatabaseReference
    private lateinit var checkoutPutriBinding: ActivityCheckoutPutriBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkoutPutriBinding = ActivityCheckoutPutriBinding.inflate(layoutInflater)
        setContentView(checkoutPutriBinding.root)

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

        refData = FirebaseDatabase.getInstance().getReference("CheckoutPutri")
        val putri = intent.getParcelableExtra<kosPutri>("PutriDetail")

        Glide.with(this)
            .load(putri!!.image)
            .placeholder(R.drawable.proggres_animation)
            .into(checkoutPutriBinding.ivPosterCheckout)

        checkoutPutriBinding.tvlokasiCheckout.text = putri.tempat
        checkoutPutriBinding.tvrateCheckout.text = putri.rating
        checkoutPutriBinding.tvPrice.formatPrice(putri.harga)

        val personeName = arrayOf("B3", "B4", "B5", "B12", "B18", "B10")
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, personeName)

        val personBulan = arrayOf("1 Bulan")
        val arrayBulan = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, personBulan)

        val status = arrayOf("Mahasiswi","Pekerja","Freelance","Menikah")
        val arrayStatus = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,status)


        checkoutPutriBinding.spKamar.adapter = arrayAdapter
        checkoutPutriBinding.spKamar.onItemSelectedListener = object :

            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                checkoutPutriBinding.spinerResult.text= personeName[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(applicationContext, "Silahkan pilih kamar anda", Toast.LENGTH_SHORT).show()
            }

        }

        checkoutPutriBinding.spBulan.adapter = arrayBulan
        checkoutPutriBinding.spBulan.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                checkoutPutriBinding.spinerResultBulan.text = personBulan[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

        checkoutPutriBinding.spStatus.adapter = arrayStatus
        checkoutPutriBinding.spStatus.onItemSelectedListener = object :


            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                checkoutPutriBinding.tvKelasResult.text = status[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(applicationContext, "Silahkan isi identitas", Toast.LENGTH_SHORT).show()
            }

         }


        checkoutPutriBinding.btnCheckout.setOnClickListener {
            saveDataputri()
        }
    }


    private fun saveDataputri() {
        val putri = intent.getParcelableExtra<kosPutri>("PutriDetail")
        val imageUser = FirebaseAuth.getInstance().currentUser?.photoUrl.toString()
        val formId = FirebaseAuth.getInstance().currentUser?.uid.toString()
        val nameUser = FirebaseAuth.getInstance().currentUser?.displayName.toString()
        val spinerKamar = checkoutPutriBinding.spinerResult.text.toString()
        val spinerBulan = checkoutPutriBinding.spinerResultBulan.text.toString()
        val imagePoster = putri?.image.toString()
        val price = putri?.harga
        val rateCheck = putri?.rating.toString()
        val status = checkoutPutriBinding.tvKelasResult.text.toString()
        val fasilitas = checkoutPutriBinding.tvFasilitas.text.toString()
        val pending = checkoutPutriBinding.pending.text.toString()


        val userId = refData.push().key
        refData.setValue("Free",fasilitas)
        refData.setValue("Pending", pending)
        val userCheckoutPutri = UserCheckoutPutri(spinerKamar,spinerBulan,imagePoster,formId,nameUser,imageUser, price, rateCheck, status, fasilitas, pending)
            refData.child(userId.toString()).setValue(userCheckoutPutri).addOnCompleteListener {
                val intent = Intent(this, PaymentPutriActivity::class.java)
                    .putExtra("putri", putri)
                startActivity(intent)
            }

    }
}