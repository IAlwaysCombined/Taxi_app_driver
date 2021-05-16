package com.example.taxi_app_driver.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.taxi_app_driver.R
import com.example.taxi_app_driver.database.AUTH
import com.example.taxi_app_driver.databinding.FragmentEnterPhoneBinding
import com.example.taxi_app_driver.uitlities.*
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

@Suppress("DEPRECATION")
class EnterPhoneFragment : Fragment(R.layout.fragment_enter_phone) {

    private lateinit var phoneNumber: String
    private lateinit var callback: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private lateinit var binding: FragmentEnterPhoneBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEnterPhoneBinding.bind(view)
    }

    override fun onStart() {
        super.onStart()

        //Callback return result auth
        callback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            //Login completed
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                AUTH.signInWithCredential(credential).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        showToast("Добро пожаловать")
                        restartActivity()
                    } else showToast(task.exception?.message.toString())
                }
            }

            //Login error
            override fun onVerificationFailed(p0: FirebaseException) {
                showToast(p0.message.toString())
            }

            //Login for the first time
            override fun onCodeSent(id: String, token: PhoneAuthProvider.ForceResendingToken) {
                replaceFragment(
                    EnterCodeFragment(
                        phoneNumber,
                        id
                    )
                )
            }
        }
        binding.enterPhoneBtnFurther.setOnClickListener { sendCode() }
    }

    //Change edit number phone
    private fun sendCode() {
        if (binding.enterPhoneEdtText.text.toString().isEmpty()) {
            showToast("Введите номер телефона")
        } else {
            authUser()
        }
    }

    //Init auth user
    private fun authUser() {
        phoneNumber = binding.enterPhoneEdtText.text.toString()
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            phoneNumber,
            60,
            TimeUnit.SECONDS,
            APP_ACTIVITY,
            callback
        )
    }
}