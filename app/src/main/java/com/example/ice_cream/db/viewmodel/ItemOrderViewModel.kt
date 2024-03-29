package com.example.ice_cream.db.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.ice_cream.model.ItemOrder
import com.example.ice_cream.db.repository.ItemOrderRepository
import kotlinx.coroutines.launch

class ItemOrderViewModel(app: Application, private val itemOrderRepository: ItemOrderRepository) :AndroidViewModel(app) {

    var itemOrderCount = 0
    val allItemOrders: LiveData<List<ItemOrder>> = itemOrderRepository.getAllItemOrders()

    fun addItemOrder(itemOrder: ItemOrder) =
        viewModelScope.launch {
            itemOrderRepository.insertItemOrder(itemOrder)
        }

    fun deleteItemOrder(itemOrder: ItemOrder) =
        viewModelScope.launch {
            itemOrderRepository.deleteItemOrder(itemOrder)
        }
}