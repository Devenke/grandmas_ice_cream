package com.example.ice_cream

import com.example.ice_cream.db.viewmodel.ItemOrderViewModelProvider
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ice_cream.adapter.ItemOrderAdapter
import com.example.ice_cream.databinding.ActivityShoppingCartBinding
import com.example.ice_cream.model.ItemOrder
import com.example.ice_cream.utilities.GIT_ORDER_PLACEMENT_URL
import com.example.ice_cream.db.viewmodel.ItemOrderViewModel
import com.example.ice_cream.service.OrderPlacementApi
import com.example.ice_cream.utilities.CHECK_RESPONSE_TAG
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ShoppingCartActivity : AppCompatActivity() {
    private lateinit var selectExtraBinding: SelectExtraActivity
    private lateinit var binding: ActivityShoppingCartBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ItemOrderAdapter
    private lateinit var itemOrderViewModel: ItemOrderViewModel

    private lateinit var cartImage: ImageView
    private lateinit var itemOrderCountText: TextView
    private lateinit var placeOrderButton: Button

    private lateinit var orders: List<ItemOrder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoppingCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        selectExtraBinding = SelectExtraActivity()
        itemOrderViewModel = ItemOrderViewModelProvider.getItemOrderViewModel(this)

        placeOrderButton = binding.placeOrderButton
        itemOrderCountText = binding.headerBar.itemCountText
        itemOrderCountText.text = itemOrderViewModel.itemOrderCount.toString()

        cartImage = binding.headerBar.shoppingCartImage
        cartImage.isEnabled = false

        recyclerView = binding.cartRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)


        itemOrderViewModel.allItemOrders.observe(this) { list ->
            orders = list
            adapter = ItemOrderAdapter(list, this) {
                itemOrderViewModel.deleteItemOrder(it)
                itemOrderViewModel.itemOrderCount--
                itemOrderCountText.text = itemOrderViewModel.itemOrderCount.toString()
            }
            recyclerView.adapter = adapter
        }
    }

    private fun placeOrders() {
        val retrofit = Retrofit.Builder()
            .baseUrl(GIT_ORDER_PLACEMENT_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(OrderPlacementApi::class.java)

        service.sendOrders(orders).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Log.i(CHECK_RESPONSE_TAG, "onSuccess")
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.i(CHECK_RESPONSE_TAG, "onFailure: ${t.message}")
            }
        })
    }
}