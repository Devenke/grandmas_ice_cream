package com.example.ice_cream

import com.example.ice_cream.viewmodel.ItemOrderViewModelProvider
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ice_cream.adapter.ItemOrderAdapter
import com.example.ice_cream.databinding.ActivityShoppingCartBinding
import com.example.ice_cream.viewmodel.ItemOrderViewModel

class ShoppingCartActivity : AppCompatActivity() {
    private lateinit var selectExtraBinding: SelectExtraActivity
    private lateinit var binding: ActivityShoppingCartBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ItemOrderAdapter
    private lateinit var itemOrderViewModel: ItemOrderViewModel

    private lateinit var cartImage: ImageView
    private lateinit var itemOrderCountText: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoppingCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        selectExtraBinding = SelectExtraActivity()
        itemOrderViewModel = ItemOrderViewModelProvider.getItemOrderViewModel(this)

        itemOrderCountText = binding.headerBar.itemCountText
        itemOrderCountText.text = itemOrderViewModel.itemOrderCount.toString()

        cartImage = binding.headerBar.shoppingCartImage
        cartImage.isEnabled = false

        recyclerView = binding.cartRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)


        itemOrderViewModel.allItemOrders.observe(this) { list ->
            adapter = ItemOrderAdapter(list, this) {
                itemOrderViewModel.deleteItemOrder(it)
                itemOrderViewModel.itemOrderCount--
                itemOrderCountText.text = itemOrderViewModel.itemOrderCount.toString()
            }
            recyclerView.adapter = adapter
        }

    }
}