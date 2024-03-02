package com.example.ice_cream.model.dto

import com.example.ice_cream.model.IceCream
import com.google.gson.annotations.SerializedName

data class IceCreamResponse (
    @SerializedName("basePrice") val basePrice: Int,
    @SerializedName("iceCreams") val iceCreams: List<IceCream>
)