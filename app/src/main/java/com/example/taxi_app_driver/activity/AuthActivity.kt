package com.example.taxi_app_driver.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.taxi_app_driver.databinding.ActivityAuthBinding
import com.example.taxi_app_driver.ui.fragment.EnterPhoneFragment
import com.example.taxi_app_driver.uitlities.replaceFragment

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        replaceFragment(EnterPhoneFragment())
    }
}