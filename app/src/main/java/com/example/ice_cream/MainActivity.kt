package com.example.ice_cream

import StatusDeserializer
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ice_cream.adapter.IceCreamAdapter
import com.example.ice_cream.databinding.ActivityMainBinding
import com.example.ice_cream.enums.Status
import com.example.ice_cream.model.IceCream
import com.example.ice_cream.model.dto.IceCreamResponse
import com.example.ice_cream.service.IceCreamApi
import com.example.ice_cream.utilities.CHECK_RESPONSE_TAG
import com.example.ice_cream.utilities.GIT_BASE_URL
import com.example.ice_cream.utilities.ICE_CREAMS_DATA_PATH
import com.example.ice_cream.utilities.MISSING_IMAGE_URL
import com.example.ice_cream.viewmodel.ItemOrderViewModel
import com.example.ice_cream.viewmodel.ItemOrderViewModelProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var adapter: IceCreamAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var itemOrderCountText: TextView
    private lateinit var itemOrderViewModel: ItemOrderViewModel
    private lateinit var spinner: Spinner

    private var iceCreams: List<IceCream> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        itemOrderViewModel = ItemOrderViewModelProvider.getItemOrderViewModel(this)
        itemOrderCountText = binding.headerBar.itemCountText

        itemOrderViewModel.allItemOrders.observe(this) {
            itemOrderViewModel.itemOrderCount = it.size
            itemOrderCountText.text = itemOrderViewModel.itemOrderCount.toString()
        }

        databaseReference = FirebaseDatabase.getInstance().getReference(ICE_CREAMS_DATA_PATH)
        recyclerView = binding.mainRecycleView
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = IceCreamAdapter(emptyList(), this)
        getAllIceCreams()
        recyclerView.adapter = adapter

        setupSpinner()

    }
/*
    private fun getAllIceCreams() {
        databaseReference = FirebaseDatabase.getInstance().getReference(ICE_CREAMS_DATA_PATH)
        databaseReference.child(ICE_CREAMS_DATA_CHILD_PATH).get().addOnSuccessListener { dataSnapshot ->
            if (dataSnapshot.exists()) {
                dataSnapshot.children.forEach { snapshot ->
                    snapshot.getValue(IceCream::class.java)?.let { iceCreams.add(it) }
                }
                adapter.iceCreams = iceCreams
                adapter.notifyDataSetChanged()
            } else {
                Toast.makeText(this, NOTIFICATION_OUT_OF_ICE_CREAM, Toast.LENGTH_SHORT).show()
            }
        }
    }
*/
    private fun getAllIceCreams() {
        val gsonBuilder = GsonBuilder()
            .registerTypeAdapter(Status::class.java, StatusDeserializer())
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl(GIT_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
            .build()
            .create(IceCreamApi::class.java)

        retrofit.getIceCreams().enqueue(object : Callback<IceCreamResponse> {
            override fun onResponse(
                call: Call<IceCreamResponse>,
                response: Response<IceCreamResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        adapter.iceCreams = it.iceCreams.apply {
                            this[2].imageUrl = MISSING_IMAGE_URL
                        }
                        iceCreams = it.iceCreams
                        adapter.notifyDataSetChanged()
                    }
                }
            }

            override fun onFailure(call: Call<IceCreamResponse>, t: Throwable) {
                Log.i(CHECK_RESPONSE_TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun setupSpinner() {
        spinner = findViewById(R.id.spinner)
        val options = listOf("No sorting", "Available", "Melted", "Unavailable")
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, options)
        spinner.adapter = arrayAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> sortIceCreamsByStatus(null)
                    1 -> sortIceCreamsByStatus(Status.AVAILABLE)
                    2 -> sortIceCreamsByStatus(Status.MELTED)
                    3 -> sortIceCreamsByStatus(Status.UNAVAILABLE)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }

    private fun sortIceCreamsByStatus(status: Status?) {
        val sortedList = when (status) {
            Status.AVAILABLE -> iceCreams.sortedBy { it.status != Status.AVAILABLE }
            Status.MELTED -> iceCreams.sortedBy { it.status != Status.MELTED }
            Status.UNAVAILABLE -> iceCreams.sortedBy { it.status != Status.UNAVAILABLE }
            else -> iceCreams
        }
        adapter.iceCreams = sortedList
        adapter.notifyDataSetChanged()
    }

    fun navigateToCart(view: View) {
        val intent = Intent(this, ShoppingCartActivity::class.java)
        startActivity(intent)
    }
}