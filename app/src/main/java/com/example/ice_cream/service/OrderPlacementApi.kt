package com.example.ice_cream.service

import com.example.ice_cream.model.ItemOrder
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface OrderPlacementApi {
    @POST
    fun sendOrders(@Body orders: List<ItemOrder>): Call<Void>
}