package com.example.ice_cream.model

import com.example.ice_cream.enums.Status
import java.io.Serializable

class IceCream (
    val id: Long? = null,
    val name: String? = null,
    val status: Status? = null,
    val imageUrl: String? = null
): Serializable