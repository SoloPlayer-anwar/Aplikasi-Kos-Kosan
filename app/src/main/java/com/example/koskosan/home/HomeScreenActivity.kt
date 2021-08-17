package com.example.koskosan.home

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.example.koskosan.R
import com.example.koskosan.chat.ChatFragment
import com.example.koskosan.dashboard.DashboardFragment
import com.example.koskosan.databinding.ActivityHomeScreenBinding
import com.example.koskosan.lokasi.LokasiFragment
import com.example.koskosan.waiting.WaitingFragment


open class HomeScreenActivity : AppCompatActivity() {

    private lateinit var homeScreenBinding: ActivityHomeScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeScreenBinding = ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(homeScreenBinding.root)


        replaceFragment(DashboardFragment.newInstance())
        homeScreenBinding.bottomNavigation.show(0)
        homeScreenBinding.bottomNavigation.add(MeowBottomNavigation.Model(0, R.drawable.ic_baseline_home_24))
        homeScreenBinding.bottomNavigation.add(MeowBottomNavigation.Model(1, R.drawable.ic_baseline_loyalty_24))
        homeScreenBinding.bottomNavigation.add(MeowBottomNavigation.Model(2, R.drawable.ic_baseline_comment_24))
        homeScreenBinding.bottomNavigation.add(MeowBottomNavigation.Model(3, R.drawable.ic_baseline_drive_eta_24))


        homeScreenBinding.bottomNavigation.setOnClickMenuListener {
            when(it.id) {
                0 -> {
                    replaceFragment(DashboardFragment.newInstance())
                }

                1 -> {
                    replaceFragment(WaitingFragment.newInstance())
                }


                2 -> {
                    replaceFragment(ChatFragment.newInstance())
                }


                3 -> {
                    replaceFragment(LokasiFragment.newInstance())
                }

            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(R.id.fragmentContainer,fragment)
        fragmentTransition.commit()
    }

}