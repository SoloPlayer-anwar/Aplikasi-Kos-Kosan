package com.example.koskosan


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.koskosan.databinding.ActivityMainBinding
import com.example.koskosan.google.LoginGoogleActivity
import com.example.koskosan.home.HomeScreenActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var Auth: FirebaseAuth
    private lateinit var mainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)



        Auth = FirebaseAuth.getInstance()
        val user = Auth.currentUser



        Handler(Looper.myLooper()!!).postDelayed({
            if (user != null) {
                val homeIntent = Intent(this, HomeScreenActivity::class.java)
                startActivity(homeIntent)
                finishAffinity()
            }else {
                val signIntent = Intent(this, LoginGoogleActivity::class.java )
                startActivity(signIntent)
                finish()
            }
        }, 3000)

    }

}
