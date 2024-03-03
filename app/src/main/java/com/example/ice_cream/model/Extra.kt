package com.example.ice_cream.model

import com.example.ice_cream.enums.Type

class Extra(
    val required: Boolean? = null,
    val type: String,
    val items: List<Item>
)