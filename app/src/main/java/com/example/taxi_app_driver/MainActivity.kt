package com.example.taxi_app_driver

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.example.taxi_app_driver.activity.AuthActivity
import com.example.taxi_app_driver.databinding.ActivityMainBinding
import com.example.taxi_app_driver.models.Driver
import com.example.taxi_app_driver.ui.`object`.AppDrawer
import com.example.taxi_app_driver.ui.fragment.MapsFragment
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
    }

    override fun onStart() {
        super.onStart()
        initFields()
        initFunc()
    }

    private fun initFields(){
        toolbar = binding.mainToolbar
        appDrawer = AppDrawer(toolbar)
        setSupportActionBar(toolbar)
        appDrawer.create()
        initFirebase()
        initDriver()
    }

    private fun initFunc(){
        if (AUTH.currentUser != null) {
            replaceFragment(MapsFragment())
        }
        else{
            replaceActivity(AuthActivity())

        }
    }
}