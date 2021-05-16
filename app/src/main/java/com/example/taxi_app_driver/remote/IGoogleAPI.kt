package com.example.taxi_app_driver.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface IGoogleAPI {
    @GET("maps/api/direction/json")
    fun getDirections(
            @Query("mode")mode: String?,
            @Query("transit_routing_preference")transit_routing: String?,
            @Query("origin")from: String?,
            @Query("destination")to: String?,
            @Query("key")key: String?
    )
}