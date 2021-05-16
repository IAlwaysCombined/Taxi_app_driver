package com.example.taxi_app_driver.database

import com.example.taxi_app_driver.models.CommonModel
import com.example.taxi_app_driver.models.DriverModel
import com.example.taxi_app_driver.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference

lateinit var AUTH: FirebaseAuth
lateinit var UID: String
lateinit var REF_DATABASE_ROOT: DatabaseReference
lateinit var PHONE: String
lateinit var USER: User
lateinit var COMMON: CommonModel
lateinit var DRIVER: DriverModel

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