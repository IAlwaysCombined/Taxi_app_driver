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
const val NODE_DRIVER = "driver"
const val NODE_PRE_RIDES = "pre_rides"
const val NODE_RIDES = "rides"
const val NODE_RIDES_DRIVER = "rides_driver"

const val NODE_RIDES_END_DRIVER = "rides_driver_end"
const val NODE_RIDES_FOR_ADMIN = "rides_for_admin"


//Driver const
const val CHILD_ROLE = "role"
const val CHILD_BLOC = "bloc"
const val CHILD_UNBLOCK = "unblock"

//Role driver
const val DRIVER_ROLE = "driver"

//Rides const
const val CHILD_START = "start_ride"
const val CHILD_END = "end_ride"
const val CHILD_STOP = "center_ride"
const val CHILD_COAST = "coast_ride"
const val CHILD_PHONE = "phone_user"
const val CHILD_NAME_USER = "name_user"
const val CHILD_PAY_METHOD = "pay_method"

const val CHILD_NAME_DRIVER = "name_driver"
const val CHILD_SURNAME_DRIVER = "surname_driver"
const val CHILD_LASTNAME_DRIVER = "last_name_driver"
const val CHILD_PHONE_DRIVER = "phone_number_driver"
const val CHILD_PHOTO_DRIVER = "photo_driver"

