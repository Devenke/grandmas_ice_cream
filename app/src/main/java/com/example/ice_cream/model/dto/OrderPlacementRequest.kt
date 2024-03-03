package com.example.ice_cream.model.dto

data class OrderPlacementRequest (
    val iceCreamId: Long,
    val extras: List<Long>
)