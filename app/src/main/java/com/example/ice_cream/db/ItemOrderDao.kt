package com.example.ice_cream.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.ice_cream.model.ItemOrder

@Dao
interface ItemOrderDao {
    @Insert
    suspend fun insertItemOrder(itemOrder: ItemOrder)

    @Query("SELECT * FROM itemOrder")
    fun getAllItemOrders(): LiveData<List<ItemOrder>>

    @Delete
    suspend fun deleteItemOrder(itemOrder: ItemOrder)
}