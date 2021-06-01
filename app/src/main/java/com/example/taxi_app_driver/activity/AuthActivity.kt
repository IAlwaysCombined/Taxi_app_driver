package com.example.taxi_app_driver.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.taxi_app_driver.MainActivity
import com.example.taxi_app_driver.R
import com.example.taxi_app_driver.database.*
import com.example.taxi_app_driver.databinding.ActivityAuthBinding
import com.example.taxi_app_driver.uitlities.replaceActivity
import com.example.taxi_app_driver.uitlities.showToast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        binding.authTextViewRestorePassword.setOnClickListener { restorePassword() }
        binding.authBtnEnter.setOnClickListener { changeLoginAndPassword() }
    }

    //Restore password
    private fun restorePassword() {
        val emailDriver = binding.authEdtTextEmail.text.toString()
        if (emailDriver.isEmpty()) {
            showToast(getString(R.string.email_edt_text_not_filled_toast))
            return
        }
        val emailAddress = binding.authEdtTextEmail.text.toString()
        AUTH.sendPasswordResetEmail(emailAddress)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) showToast(getString(R.string.message_restore_password_send_toast))
                else showToast(getString(R.string.something_went_wrong_toast))
            }
    }

    //Change login, password, role user
    private fun changeLoginAndPassword() {
        val emailDriver = binding.authEdtTextEmail.text.toString()
        val passwordAdmin = binding.authEdtTextPassword.text.toString()
        if (emailDriver.isEmpty()) {
            showToast(getString(R.string.email_edt_text_not_filled_toast))
            return
        } else if (passwordAdmin.isEmpty()) {
            showToast(getString(R.string.password_edt_text_not_filled_toast))
            return
        }
        AUTH.signInWithEmailAndPassword(emailDriver, passwordAdmin)
            .addOnCompleteListener { task ->
                val uid = AUTH.currentUser?.uid.toString()
                REF_DATABASE_ROOT.child(NODE_DRIVER).child(uid)
                    .addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val roleUser = snapshot.child(CHILD_ROLE).value.toString()
                            val blockCheck = snapshot.child(CHILD_BLOC).value.toString()
                            if (task.isSuccessful && roleUser == DRIVER_ROLE && blockCheck == CHILD_UNBLOCK) {
                                replaceActivity(MainActivity())
                            } else {
                                showToast(getString(R.string.something_went_wrong_toast))
                                AUTH.signOut()
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {

                        }

                    })
            }
    }
}