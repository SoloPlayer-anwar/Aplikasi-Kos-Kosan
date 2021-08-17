package com.example.koskosan.lokasi

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.koskosan.R
import com.example.koskosan.chat.ChatFragment
import com.example.koskosan.databinding.FragmentLokasiBinding
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.annotations.MarkerOptions
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.Style


class LokasiFragment : Fragment(){
    private lateinit var lokasiBinding: FragmentLokasiBinding
    lateinit var  mapview: MapView
    lateinit var permissionsManager: PermissionsManager
    lateinit var  MapboxMap : MapboxMap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Mapbox.getInstance(requireContext(),getString(R.string.mapbox_access_token))
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        lokasiBinding = FragmentLokasiBinding.inflate(layoutInflater)
        return (lokasiBinding.root)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            LokasiFragment().apply {
                arguments = Bundle().apply {  }
            }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lokasiBinding.btnGo.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.co.id/maps/place/Jl.+Komp.+Gardu+Mekar+Indah,+Sungai+Lulut,+Kec.+Banjarmasin+Tim.,+Kota+Banjarmasin,+Kalimantan+Selatan+70653/@-3.323253,114.6261479,18z/data=!4m5!3m4!1s0x2de42403fc9da257:0x122efc5fcd72358e!8m2!3d-3.3225859!4d114.6267901?hl=id"))
            startActivity(intent)

        }

        mapview = view.findViewById(R.id.mapView)
        mapview.onCreate(savedInstanceState)
        mapview.getMapAsync{
            it.setStyle(Style.MAPBOX_STREETS)
            val location = LatLng(-3.324187469754645, 114.62686138559192)
            val position = CameraPosition.Builder()
                .target(LatLng(-3.324187469754645, 114.62686138559192))
                .zoom(16.0)
                .tilt(3.0)
                .bearing(20.0)
                .build()


            it.animateCamera(CameraUpdateFactory.newCameraPosition(position), 5000)
            it.addMarker(MarkerOptions().setPosition(location).title("Tempat Bapak Kos"))

        }

    }


}




