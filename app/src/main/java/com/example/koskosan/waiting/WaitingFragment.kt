package com.example.koskosan.waiting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.koskosan.databinding.FragmentWaitingBinding
import com.example.koskosan.home.ProgressDialog
import com.example.koskosan.model.UserCheckout
import com.example.koskosan.model.UserCheckoutPutri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class WaitingFragment : Fragment() {

    private var dataPutra = ArrayList<UserCheckout>()
    private var dataPutri = ArrayList<UserCheckoutPutri>()
    private lateinit var  dataBasePutri : DatabaseReference
    private lateinit var dataBasePutra : DatabaseReference
    private lateinit var mAuth: FirebaseAuth
    private lateinit var waitingBinding: FragmentWaitingBinding
    private var adapterWait : AdapterWait? = null
    private var adapterWaitPutri : AdapterWaitPutri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        waitingBinding = FragmentWaitingBinding.inflate(layoutInflater)
        return (waitingBinding.root)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            WaitingFragment().apply {
                arguments = Bundle().apply {  }
            }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        waitingBinding.rvWait.layoutManager = LinearLayoutManager(context)
        waitingBinding.rvWaitPutri.layoutManager = LinearLayoutManager(context)
        adapterWait = AdapterWait(dataPutra) {
            Toast.makeText(context, "Status Masih Pending..",Toast.LENGTH_LONG).show()
        }
        adapterWaitPutri = AdapterWaitPutri(dataPutri) {
            Toast.makeText(context, "Status Masih Pending..",Toast.LENGTH_LONG).show()
        }


         dataBasePutri = FirebaseDatabase.getInstance().getReference("CheckoutPutri")
         dataBasePutra = FirebaseDatabase.getInstance().getReference("Checkout")

        showDataPutra()
        showDataPutri()
    }

    private fun showDataPutri() {
        val show = ProgressDialog(requireActivity())
        show.showLoading()
        dataBasePutri.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                show.dismissLoading()
                for (item in snapshot.children) {
                    val putri = item.getValue(UserCheckoutPutri::class.java)
                    dataPutri.add(0, putri!!)
                }
                if (dataPutri.isNotEmpty()) {
                    waitingBinding.rvWaitPutri.adapter = adapterWaitPutri
                }


            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "${error.message}",Toast.LENGTH_SHORT).show()
                show.showLoading()
            }

        })
    }



    private fun showDataPutra() {
        val show = ProgressDialog(requireActivity())
        show.showLoading()
        dataBasePutra.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                dataPutra.clear()
                show.dismissLoading()
                for (item in snapshot.children) {
                    val putra = item.getValue(UserCheckout::class.java)
                    dataPutra.add(0, putra!!)
                }

                if (dataPutra.isNotEmpty()) {
                    waitingBinding.rvWait.adapter = adapterWait
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "${error.message}",Toast.LENGTH_SHORT).show()
                show.showLoading()
            }

        })
    }
}