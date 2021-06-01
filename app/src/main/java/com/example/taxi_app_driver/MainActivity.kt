package com.example.taxi_app_driver

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.example.taxi_app_driver.activity.AuthActivity
import com.example.taxi_app_driver.database.*
import com.example.taxi_app_driver.database.initUser
import com.example.taxi_app_driver.databinding.ActivityMainBinding
import com.example.taxi_app_driver.ui.`object`.AppDrawer
import com.example.taxi_app_driver.ui.fragment.driver.rides.ListRidesFragment
import com.example.taxi_app_driver.uitlities.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var toolbar: Toolbar
    lateinit var appDrawer: AppDrawer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        APP_ACTIVITY = this
        initFirebase()
        initPreRide()
        initUser()
        initDriver{
            initFields()
            initFunc()
        }
    }

    private fun initFields(){
        toolbar = binding.mainToolbar
        appDrawer = AppDrawer(toolbar)
    }

    private fun initFunc(){
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        if (AUTH.currentUser != null) {
             appDrawer.create()
            replaceFragment(ListRidesFragment())
        }
        else{
            replaceActivity(AuthActivity())
        }
    }
}