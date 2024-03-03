package com.example.ice_cream.service

import com.example.ice_cream.model.Extra
import com.example.ice_cream.utilities.GIT_EXTRAS_URL
import retrofit2.Call
import retrofit2.http.GET

interface ExtrasApi {
    @GET(GIT_EXTRAS_URL)
    fun getExtras(): Call<List<Extra>>
}
