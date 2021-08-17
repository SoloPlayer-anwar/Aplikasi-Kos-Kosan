package com.example.koskosan.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.koskosan.R
import com.example.koskosan.dashboard.seemore.PutraSeeMoreActivity
import com.example.koskosan.dashboard.seemore.PutriSeeMoreActivity
import com.example.koskosan.databinding.FragmentDashboardBinding
import com.example.koskosan.home.ProgressDialog
import com.example.koskosan.model.ImageData
import com.example.koskosan.model.kosPutra
import com.example.koskosan.model.kosPutri
import com.example.koskosan.profile.ProfileActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlin.concurrent.fixedRateTimer


class DashboardFragment : Fragment(R.layout.fragment_dashboard) {

    private var mDatabaseReference: DatabaseReference? = null
    private var dataPutra = ArrayList<kosPutra>()
    private var dataPutri = ArrayList<kosPutri>()
    lateinit var mAuth: FirebaseAuth
    private var list = ArrayList<ImageData>()



    private lateinit var dashboardBinding: FragmentDashboardBinding
    private lateinit var adapter : ImageSliderAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardBinding = FragmentDashboardBinding.inflate(layoutInflater)
        return (dashboardBinding.root)


    }

    companion object {
        @JvmStatic
        fun newInstance() =
            DashboardFragment().apply {
                arguments = Bundle().apply {  }
            }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getKosputra()
        getkosPutri()

        dashboardBinding.seemore.setOnClickListener {
            val intent = Intent(requireContext(), PutraSeeMoreActivity::class.java)
            startActivity(intent)
        }

        dashboardBinding.seemorePutri.setOnClickListener {
            val intent = Intent(requireContext(), PutriSeeMoreActivity::class.java)
            startActivity(intent)
        }

        dashboardBinding.rvKosputra.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        dashboardBinding.rvKosputri.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        mAuth = FirebaseAuth.getInstance()
        val imageUser = mAuth.currentUser

        Glide.with(this)
            .load(imageUser?.photoUrl)
            .placeholder(R.drawable.proggres_animation)
            .into(dashboardBinding.ivProfile)

        list.add(
            ImageData(
                "https://firebasestorage.googleapis.com/v0/b/tesproject-6bee9.appspot.com/o/Frame%203.jpg?alt=media&token=286aae95-06c9-4321-a5a3-c3ba1db16d91"
            )
        )

        list.add(
            ImageData(
                "https://firebasestorage.googleapis.com/v0/b/tesproject-6bee9.appspot.com/o/Frame%204.jpg?alt=media&token=73f74745-1787-4d3d-bbc0-03c040661324"
            )
        )

        adapter = ImageSliderAdapter(list)
        dashboardBinding.viewPager.adapter = adapter


        dashboardBinding.ivProfile.setOnClickListener {
            val intent = Intent (context, ProfileActivity::class.java)
            startActivity(intent)
        }


    }



    private fun getkosPutri() {
        val show = ProgressDialog(this.requireActivity())
        show.showLoading()
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("kosputri")
        mDatabaseReference?.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                show.dismissLoading()
                dataPutri.clear()
                for (item in snapshot.children) {
                    val putri = item.getValue(kosPutri::class.java)
                    dataPutri.add(putri!!)
                }

                if (dataPutri.isNotEmpty()) {
                    dashboardBinding.rvKosputri.adapter = KosPutriAdapter(dataPutri) {
                        val intent = Intent(requireContext(), DetailPutriActivity::class.java)
                            .putExtra("putri", it)
                        startActivity(intent)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
               Toast.makeText(context, "${error.message}",Toast.LENGTH_SHORT).show()
                show.showLoading()
            }

        })
    }

    private fun getKosputra() {
        val show = ProgressDialog(this.requireActivity())
        show.showLoading()
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("kosputra")
        mDatabaseReference?.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                show.dismissLoading()
                dataPutra.clear()
                for (item in snapshot.children) {
                    val putra = item.getValue(kosPutra::class.java)
                    dataPutra.add(0,putra!!)
                }

                if (dataPutra.isNotEmpty()) {
                    dashboardBinding.rvKosputra.adapter = KosPutraAdapter(dataPutra) {
                        val intent = Intent(requireContext(), DetailPutraActivity::class.java)
                            .putExtra("putra", it)
                        startActivity(intent)

                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "${error.message}", Toast.LENGTH_SHORT).show()
            }

        })
    }



}


