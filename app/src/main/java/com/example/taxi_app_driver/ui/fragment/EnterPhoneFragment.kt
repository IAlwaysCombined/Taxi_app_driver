package com.example.taxi_app_driver.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.taxi_app_driver.R
import com.example.taxi_app_driver.databinding.FragmentEnterPhoneBinding

class EnterPhoneFragment : Fragment(R.layout.fragment_enter_phone) {

    private lateinit var binding: FragmentEnterPhoneBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEnterPhoneBinding.bind(view)
    }

    override fun onStart() {
        super.onStart()
    }
}