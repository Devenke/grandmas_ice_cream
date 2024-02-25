package com.example.ice_cream

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ice_cream.adapter.IceCreamAdapter
import com.example.ice_cream.adapter.ItemOrderAdapter
import com.example.ice_cream.databinding.ActivityMainBinding
import com.example.ice_cream.databinding.ActivitySelectExtraBinding
import com.example.ice_cream.databinding.ActivityShoppingCartBinding
import com.example.ice_cream.db.AppDatabase
import com.example.ice_cream.repository.ItemOrderRepository
import com.example.ice_cream.viewmodel.ItemOrderViewModel
import com.example.ice_cream.viewmodel.ItemOrderViewModelFactory

class ShoppingCartActivity : AppCompatActivity() {
    private lateinit var selectExtraBinding: SelectExtraActivity
    private lateinit var binding: ActivityShoppingCartBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ItemOrderAdapter
    private lateinit var itemOrderViewModel: ItemOrderViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoppingCartBinding.inflate(layoutInflater)
        selectExtraBinding = SelectExtraActivity()
        setContentView(binding.root)
        setupViewModel()

        recyclerView = binding.cartRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)


        itemOrderViewModel.allItemOrders.observe(this) {
            adapter = ItemOrderAdapter(it, this)
            recyclerView.adapter = adapter
        }

    }

    private fun setupViewModel() {
        val itemOrderRepository = ItemOrderRepository(AppDatabase(this))
        val viewModelProviderFactory = ItemOrderViewModelFactory(application, itemOrderRepository)
        itemOrderViewModel = ViewModelProvider(this, viewModelProviderFactory)[ItemOrderViewModel::class.java]
    }
}