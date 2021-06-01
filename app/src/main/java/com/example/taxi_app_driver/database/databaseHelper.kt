package com.example.taxi_app_driver.database

import com.example.taxi_app_driver.models.CommonModel
import com.example.taxi_app_driver.models.DriverModel
import com.example.taxi_app_driver.models.User
import com.example.taxi_app_driver.uitlities.AppValueEventListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase

//Init firebase help method
fun initFirebase() {
    AUTH = FirebaseAuth.getInstance()
    USER = User()
    COMMON = CommonModel()
    DRIVER = DriverModel()
    REF_DATABASE_ROOT = FirebaseDatabase.getInstance().reference
    UID = AUTH.currentUser?.uid.toString()
    PHONE = AUTH.currentUser?.phoneNumber.toString()
}

//Get common model fun
fun DataSnapshot.getCommonModel(): CommonModel =
    this.getValue(CommonModel::class.java) ?: CommonModel()

//Initial Driver
inline fun initDriver(crossinline function: () -> Unit) {
    REF_DATABASE_ROOT.child(NODE_DRIVER).child(UID)
        .addListenerForSingleValueEvent(AppValueEventListener {
            DRIVER = it.getValue(DriverModel::class.java) ?: DriverModel()
            function()
        })
}

//Initial Users
fun initUser() {
    REF_DATABASE_ROOT.child(NODE_USERS).child(UID)
        .addListenerForSingleValueEvent(AppValueEventListener {
            USER = it.getValue(User::class.java) ?: User()
        })
}

fun initPreRide(){
    REF_DATABASE_ROOT.child(NODE_PRE_RIDES).child(COMMON.uid)
        .addListenerForSingleValueEvent(AppValueEventListener {
            COMMON = it.getValue(CommonModel::class.java) ?: CommonModel()
        })
}
