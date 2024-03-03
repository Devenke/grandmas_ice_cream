package com.example.ice_cream

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ice_cream.adapter.ExtrasAdapter
import com.example.ice_cream.databinding.ActivitySelectExtraBinding
import com.example.ice_cream.model.Extra
import com.example.ice_cream.model.IceCream
import com.example.ice_cream.model.ItemOrder
import com.example.ice_cream.service.ExtrasApi
import com.example.ice_cream.utilities.CHECK_RESPONSE_TAG
import com.example.ice_cream.utilities.EXTRA_CONE
import com.example.ice_cream.utilities.EXTRA_DRESSING
import com.example.ice_cream.utilities.EXTRA_ICE_CREAM
import com.example.ice_cream.utilities.EXTRA_OTHER
import com.example.ice_cream.utilities.GIT_BASE_URL
import com.example.ice_cream.db.viewmodel.ItemOrderViewModel
import com.example.ice_cream.db.viewmodel.ItemOrderViewModelProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SelectExtraActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySelectExtraBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ExtrasAdapter

    private lateinit var confirmButton: Button
    private lateinit var iceCreamImage: ImageView
    private lateinit var iceCream: IceCream

    private lateinit var itemOrderViewModel: ItemOrderViewModel
    private lateinit var itemOrderCountText: TextView
    private lateinit var extras: List<Extra>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectExtraBinding.inflate(layoutInflater)
        setContentView(binding.root)
        itemOrderViewModel = ItemOrderViewModelProvider.getItemOrderViewModel(this)
        itemOrderCountText = binding.headerBar.itemCountText

        recyclerView = binding.extrasRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ExtrasAdapter(emptyList(), this)
        getExtras()
        recyclerView.adapter = adapter

        itemOrderViewModel.allItemOrders.observe(this) {
            itemOrderViewModel.itemOrderCount = it.size
            itemOrderCountText.text = itemOrderViewModel.itemOrderCount.toString()
        }

        confirmButton = binding.confirmButton
        iceCreamImage = binding.selectExtraImage


        iceCream = intent.getParcelableExtra(EXTRA_ICE_CREAM)!!
        Glide.with(this).load(iceCream.imageUrl).into(iceCreamImage)

        confirmButton.setOnClickListener {
            var selectedConeId = 0
            var selectedOtherId: Int? = null
            var selectedDressingId: Int? = null

            for (i in 0 until recyclerView.childCount) {
                val viewHolder = recyclerView.findViewHolderForAdapterPosition(i) as ExtrasAdapter.ExtrasViewHolder
                val selectedExtraName = viewHolder.spinner.selectedItem as String
                for (extra in extras) {
                    val selectedItem = extra.items.find { it.name == selectedExtraName }
                    selectedItem?.let {
                        when (extra.type) {
                            EXTRA_CONE -> selectedConeId = it.id!!
                            EXTRA_OTHER -> selectedOtherId = it.id
                            EXTRA_DRESSING -> selectedDressingId = it.id
                        }
                    }
                }
            }

            itemOrderViewModel.addItemOrder(
                ItemOrder(
                    0,
                    iceCream.id!!,
                    iceCream.name!!,
                    selectedConeId,
                    selectedDressingId,
                    selectedOtherId
                )
            )
            itemOrderViewModel.itemOrderCount++
            itemOrderCountText.text = itemOrderViewModel.itemOrderCount.toString()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun getExtras() {
        val retrofit = Retrofit.Builder()
            .baseUrl(GIT_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ExtrasApi::class.java)

        retrofit.getExtras().enqueue(object : Callback<List<Extra>> {
            override fun onResponse(
                call: Call<List<Extra>>,
                response: Response<List<Extra>>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        extras = it
                        adapter.extras = it
                        adapter.notifyDataSetChanged()
                    }
                }
            }

            override fun onFailure(call: Call<List<Extra>>, t: Throwable) {
                Log.i(CHECK_RESPONSE_TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun navigateToCart(view: View) {
        val intent = Intent(this, ShoppingCartActivity::class.java)
        startActivity(intent)
    }
}
