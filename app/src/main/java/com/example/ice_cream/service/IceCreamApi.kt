package com.example.ice_cream.service

import com.example.ice_cream.model.dto.IceCreamResponse
import com.example.ice_cream.utilities.GIT_ICE_CREAM_URL
import retrofit2.Call
import retrofit2.http.GET

interface IceCreamApi {
    @GET(GIT_ICE_CREAM_URL)
    fun getIceCreams(): Call<IceCreamResponse>
}