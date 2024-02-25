package com.example.ice_cream.repository

import androidx.lifecycle.LiveData
import com.example.ice_cream.db.AppDatabase
import com.example.ice_cream.model.ItemOrder

class ItemOrderRepository(private val db: AppDatabase) {

    suspend fun insertItemOrder(itemOrder: ItemOrder) = db.getItemOrderDao().insertItemOrder(itemOrder)
    suspend fun deleteItemOrder(itemOrder: ItemOrder) = db.getItemOrderDao().deleteItemOrder(itemOrder)

    fun getAllItemOrders(): LiveData<List<ItemOrder>> {
        return db.getItemOrderDao().getAllItemOrders()
    }}