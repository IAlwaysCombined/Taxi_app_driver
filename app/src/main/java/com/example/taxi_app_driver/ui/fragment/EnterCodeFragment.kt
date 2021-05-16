package com.example.taxi_app_driver.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.taxi_app_driver.MainActivity
import com.example.taxi_app_driver.R
import com.example.taxi_app_driver.database.*
import com.example.taxi_app_driver.databinding.FragmentEnterCodeBinding
import com.example.taxi_app_driver.uitlities.*
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class EnterCodeFragment(private val phoneNumber: String, val id: String) : Fragment(R.layout.fragment_enter_code) {

    private lateinit var binding: FragmentEnterCodeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEnterCodeBinding.bind(view)
    }

    override fun onStart() {
        super.onStart()
        binding.enterCodeEdtText.addTextChangedListener(AppTextWatcher {
            val string = binding.enterCodeEdtText.text.toString()
            if (string.length == 6) {
                binding.enterCodeBtnFurther.setOnClickListener { enterCode() }
            }
        })
    }

    //Enter code
    private fun enterCode() {
        val code = binding.enterCodeEdtText.text.toString()
        val credential = PhoneAuthProvider.getCredential(id, code)
        AUTH.signInWithCredential(credential).addOnCompleteListener { task ->
            val uid = AUTH.currentUser?.uid.toString()
            REF_DATABASE_ROOT.child(NODE_DRIVER).child(uid).addValueEventListener(object:
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val roleUser = snapshot.child(CHILD_ROLE).value.toString()
                    if (task.isSuccessful && roleUser == DRIVER_ROLE) {
                        showToast("Добро пожаловать")
                        replaceActivity(MainActivity())
                    } else{
                        showToast("Вы не являетесь водителем!")
                        AUTH.signOut()
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })

        }
    }
}