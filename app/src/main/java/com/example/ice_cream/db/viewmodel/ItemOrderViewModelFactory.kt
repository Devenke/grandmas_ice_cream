package com.example.ice_cream.db.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ice_cream.db.repository.ItemOrderRepository

class ItemOrderViewModelFactory(val app: Application, private val itemOrderRepository: ItemOrderRepository) :  ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ItemOrderViewModel(app, itemOrderRepository) as T
    }
}