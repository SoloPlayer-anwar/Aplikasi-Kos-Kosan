package com.example.koskosan.dashboard.seemore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import com.example.koskosan.dashboard.DashboardFragment
import com.example.koskosan.dashboard.DetailPutriActivity
import com.example.koskosan.dashboard.KosPutraAdapter
import com.example.koskosan.dashboard.KosPutriAdapter
import com.example.koskosan.databinding.ActivityPutriSeeMoreBinding
import com.example.koskosan.model.kosPutri
import com.google.firebase.database.*

class PutriSeeMoreActivity : AppCompatActivity() {
    private lateinit var putriSeeMoreBinding: ActivityPutriSeeMoreBinding
    lateinit var mDatabaseReference: DatabaseReference
    private var dataPutri = ArrayList<kosPutri>()
    private var putriAdapter: AdapterSeemorePutri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        putriSeeMoreBinding = ActivityPutriSeeMoreBinding.inflate(layoutInflater)
        setContentView(putriSeeMoreBinding.root)



        putriSeeMoreBinding.back.setOnClickListener {
            finish()
        }

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("kosputri")
        putriSeeMoreBinding.rvsearch1.layoutManager = GridLayoutManager(this, 2)
        putriSeeMoreBinding.rvsearch2.layoutManager = GridLayoutManager(this, 2)

        putriAdapter = AdapterSeemorePutri(dataPutri) {
            val intent = Intent(this, DetailPutriActivity::class.java)
                .putExtra("putri", it)
            startActivity(intent)
        }
        showDataPutri()

        putriSeeMoreBinding.searchview.imeOptions = EditorInfo.IME_ACTION_DONE
        putriSeeMoreBinding.searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(action: String?): Boolean {
                if (action!!.isEmpty()) {
                    putriSeeMoreBinding.rvsearch1.visibility = View.VISIBLE
                    putriSeeMoreBinding.rvsearch2.visibility = View.GONE
                }
                else if(action.length > 2) {
                    val filter = dataPutri.filter { it.rating!!.contains("$action", true) }
                    putriAdapter = AdapterSeemorePutri(filter as ArrayList<kosPutri>) {
                        val intent = Intent(applicationContext, DetailPutriActivity::class.java)
                            .putExtra("putri", it)
                        startActivity(intent)
                    }

                    if (dataPutri.isNotEmpty()) {
                        putriSeeMoreBinding.rvsearch1.visibility = View.INVISIBLE
                        putriSeeMoreBinding.rvsearch2.visibility = View.VISIBLE
                        putriSeeMoreBinding.rvsearch2.adapter = putriAdapter
                    }
                    else {
                        putriSeeMoreBinding.rvsearch1.visibility = View.VISIBLE
                        putriSeeMoreBinding.rvsearch2.visibility = View.GONE
                    }
                }

                return false
            }

        })
    }

    private fun showDataPutri() {
        mDatabaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                dataPutri.clear()
                for (item in snapshot.children) {
                    val putri = item.getValue(kosPutri::class.java)
                    dataPutri.add(putri!!)
                }

                if (dataPutri.isNotEmpty()) {
                    putriSeeMoreBinding.rvsearch1.adapter = putriAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@PutriSeeMoreActivity, "${error.message}", Toast.LENGTH_SHORT).show()
            }

        })
    }
}