package com.example.koskosan.dashboard.seemore

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.koskosan.dashboard.DetailPutraActivity
import com.example.koskosan.dashboard.KosPutraAdapter
import com.example.koskosan.databinding.ActivityPutraSeeMoreBinding
import com.example.koskosan.model.kosPutra
import com.google.firebase.database.*

class PutraSeeMoreActivity : AppCompatActivity() {
    lateinit var mDatabaseReference: DatabaseReference
    private var dataPutra = ArrayList<kosPutra>()
    private var putraAdapter: AdapterSeeMorePutra? = null

    private lateinit var putraSeeMoreBinding: ActivityPutraSeeMoreBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        putraSeeMoreBinding = ActivityPutraSeeMoreBinding.inflate(layoutInflater)
        setContentView(putraSeeMoreBinding.root)

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("kosputra")

        putraSeeMoreBinding.back.setOnClickListener {
            finish()
        }

        showDataSearch()
        putraSeeMoreBinding.rvsearch1.layoutManager = GridLayoutManager(this, 2)
        putraSeeMoreBinding.rvsearch2.layoutManager = GridLayoutManager(this,2)

        putraAdapter = AdapterSeeMorePutra(dataPutra) {
            val intent = Intent(this,DetailPutraActivity::class.java)
                .putExtra("putra", it)
            startActivity(intent)
            finish()
        }

      putraSeeMoreBinding.serachSeemore.imeOptions = EditorInfo.IME_ACTION_DONE
        putraSeeMoreBinding.serachSeemore.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(action: String?): Boolean {
                if (action!!.isEmpty()) {
                  putraSeeMoreBinding.rvsearch1.visibility = View.VISIBLE
                    putraSeeMoreBinding.rvsearch2.visibility = View.GONE
                }
                else if (action. length > 2) {
                    val filter = dataPutra.filter { it.rating?.contains(action, true) == true }
                    putraAdapter = AdapterSeeMorePutra(filter as ArrayList<kosPutra>) {
                        val intent = Intent(applicationContext, DetailPutraActivity::class.java)
                            .putExtra("putra", it)
                        startActivity(intent)
                    }
                    if (dataPutra.isNotEmpty()) {
                        putraSeeMoreBinding.rvsearch2.visibility = View.VISIBLE
                        putraSeeMoreBinding.rvsearch2.adapter = putraAdapter
                        putraSeeMoreBinding.rvsearch1.visibility = View.INVISIBLE
                    }else {
                        putraSeeMoreBinding.rvsearch1.visibility = View.VISIBLE
                        putraSeeMoreBinding.rvsearch2.visibility = View.GONE
                    }
                }

                return false
            }

        })

    }

    private fun showDataSearch() {
        mDatabaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                dataPutra.clear()
                for (item in snapshot.children) {
                    val putra = item.getValue(kosPutra::class.java)
                    dataPutra.add(putra!!)
                }

                if (dataPutra.isNotEmpty()) {
                    putraSeeMoreBinding.rvsearch1.adapter = putraAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext,error.message,Toast.LENGTH_SHORT).show()
            }

        })
    }
}