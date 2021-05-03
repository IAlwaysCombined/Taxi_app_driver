package com.example.taxi_app_driver

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.taxi_app_driver.databinding.ActivityMainBinding
import com.example.taxi_app_driver.uitlities.APP_ACTIVITY
import com.example.taxi_app_driver.uitlities.initFirebase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        APP_ACTIVITY = this
    }

    override fun onStart() {
        super.onStart()
        initFirebase()
    }
}