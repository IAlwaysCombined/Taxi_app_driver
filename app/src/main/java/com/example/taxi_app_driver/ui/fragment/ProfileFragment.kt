package com.example.taxi_app_driver.ui.fragment

import android.os.Bundle
import android.view.View
import com.example.taxi_app_driver.R
import com.example.taxi_app_driver.databinding.FragmentProfileBinding
import com.example.taxi_app_driver.ui.BaseFragment


class ProfileFragment : BaseFragment(R.layout.fragment_profile) {

    private lateinit var binding: FragmentProfileBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)
    }

    override fun onResume() {
        super.onResume()
    }
}