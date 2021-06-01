package com.example.taxi_app_driver.models

data class CommonModel(

    //Driver info
    val car: String = "",
    val car_number: String = "",
    val last_name_driver: String = "",
    val name_driver: String = "",
    val surname_driver: String = "",
    val phone_number_driver: String = "",
    val photo_driver: String = "",
    val photo_license: String = "",
    val uid: String = "",

    //Rides info
    val name_user: String = "",
    val phone_user: String = "",
    val pay_method: String = "",
    val start_ride: String = "",
    val end_ride: String = "",
    val point_ride: String = "",
    val coast_ride: String = "",
)