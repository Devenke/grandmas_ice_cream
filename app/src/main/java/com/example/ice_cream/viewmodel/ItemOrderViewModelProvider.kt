package com.example.ice_cream.viewmodel

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.ice_cream.db.AppDatabase
import com.example.ice_cream.repository.ItemOrderRepository

object ItemOrderViewModelProvider {
    private var itemOrderViewModel: ItemOrderViewModel? = null

    fun getItemOrderViewModel(activity: AppCompatActivity): ItemOrderViewModel {
        if (itemOrderViewModel == null) {
            initializeItemOrderViewModel(activity)
        }
        return itemOrderViewModel!!
    }

    private fun initializeItemOrderViewModel(activity: AppCompatActivity) {
        val itemOrderRepository = ItemOrderRepository(AppDatabase(activity))
        val viewModelProviderFactory = ItemOrderViewModelFactory(activity.application, itemOrderRepository)
        itemOrderViewModel = ViewModelProvider(activity, viewModelProviderFactory)[ItemOrderViewModel::class.java]
    }
}
