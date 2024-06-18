package com.example.eletriccardio.data

import com.example.eletriccardio.Car
import retrofit2.Call
import retrofit2.http.GET

interface ApiCarsService {

    @GET("cars.json")
    fun getAllCars(): Call<List<Car>>

}
