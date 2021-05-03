package com.example.taxi_app_driver.uitlities

import com.example.taxi_app_driver.models.Driver
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

lateinit var AUTH: FirebaseAuth
lateinit var UID: String
lateinit var REF_DATABASE_ROOT: DatabaseReference
lateinit var PHONE: String
lateinit var DRIVER: Driver

//Nodes
const val NODE_USERS = "users"
const val NODE_PHONES = "phones"
const val NODE_DRIVER = "driver"

//User const
const val CHILD_ID = "id"
const val CHILD_PHONE = "phone"
const val CHILD_USERNAME = "username"
const val CHILD_ROLE = "role"

//Role user
const val USER_ROLE = "user"
const val DRIVER_ROLE = "driver"

//Init firebase help method
fun initFirebase() {
    AUTH = FirebaseAuth.getInstance()
    DRIVER = Driver()
    REF_DATABASE_ROOT = FirebaseDatabase.getInstance().reference
    UID = AUTH.currentUser?.uid.toString()
    PHONE = AUTH.currentUser?.phoneNumber.toString()
}

//Initial Users
fun initDriver() {
    REF_DATABASE_ROOT.child(NODE_DRIVER).child(UID)
        .addListenerForSingleValueEvent(AppValueEventListener {
            DRIVER = it.getValue(Driver::class.java) ?: Driver()
        })
}