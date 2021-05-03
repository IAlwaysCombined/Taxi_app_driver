package com.example.taxi_app_driver.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.taxi_app_driver.R
import com.example.taxi_app_driver.databinding.FragmentEnterCodeBinding

class EnterCodeFragment : Fragment(R.layout.fragment_enter_code) {

    private lateinit var binding: FragmentEnterCodeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEnterCodeBinding.bind(view)
    }

    override fun onStart() {
        super.onStart()
    }

}