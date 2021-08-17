package com.example.koskosan.OnBoarding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.koskosan.databinding.ActivityOb1Binding

class Ob1Activity : AppCompatActivity() {
    private lateinit var ob1Binding: ActivityOb1Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ob1Binding = ActivityOb1Binding.inflate(layoutInflater)
        setContentView(ob1Binding.root)


    }
}