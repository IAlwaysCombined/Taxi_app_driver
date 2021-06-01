package com.example.taxi_app_driver.ui.fragment.driver.user

import android.os.Bundle
import android.view.View
import com.example.taxi_app_driver.R
import com.example.taxi_app_driver.activity.AuthActivity
import com.example.taxi_app_driver.database.AUTH
import com.example.taxi_app_driver.database.DRIVER
import com.example.taxi_app_driver.databinding.FragmentProfileBinding
import com.example.taxi_app_driver.ui.BaseFragment
import com.example.taxi_app_driver.uitlities.downloadAndSetImage
import com.example.taxi_app_driver.uitlities.replaceActivity


class ProfileFragment : BaseFragment(R.layout.fragment_profile) {

    private lateinit var binding: FragmentProfileBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)
    }

    override fun onResume() {
        super.onResume()
        binding.profilePhoneNumberUserTextView.text = DRIVER.phone_number_driver
        binding.profileNameUserTextView.text = DRIVER.name_driver
        binding.profileEmailUserTextView.text = DRIVER.email_driver
        binding.profileAvatarImageView.downloadAndSetImage(DRIVER.photo_driver)
        binding.profileExitBtn.setOnClickListener { exitApp() }
    }

    //Exit account
    private fun exitApp() {
        replaceActivity(AuthActivity())
        AUTH.signOut()
    }
}