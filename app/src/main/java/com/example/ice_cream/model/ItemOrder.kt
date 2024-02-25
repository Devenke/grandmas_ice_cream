package com.example.ice_cream.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "itemOrder")
class ItemOrder (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val iceCreamId: Long,
    val iceCreamName: String,
    val coneId: Int,
    val dressingId: Int? = null,
    val otherExtraId: Int? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ItemOrder

        if (id != other.id) return false
        if (iceCreamId != other.iceCreamId) return false
        if (iceCreamName != other.iceCreamName) return false
        if (coneId != other.coneId) return false
        if (dressingId != other.dressingId) return false
        return otherExtraId == other.otherExtraId
    }
}