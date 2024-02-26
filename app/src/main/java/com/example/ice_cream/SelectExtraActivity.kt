package com.example.ice_cream

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.ice_cream.databinding.ActivitySelectExtraBinding
import com.example.ice_cream.model.IceCream
import com.example.ice_cream.model.ItemOrder
import com.example.ice_cream.utilities.EXTRA_ICE_CREAM
import com.example.ice_cream.utilities.OTHER_NONE
import com.example.ice_cream.utilities.OTHER_ROLETTI
import com.example.ice_cream.utilities.OTHER_SUGAR_MAGIC
import com.example.ice_cream.utilities.OTHER_WAFER
import com.example.ice_cream.utilities.SAUCE_NONE
import com.example.ice_cream.utilities.SAUCE_STRAWBERRY
import com.example.ice_cream.utilities.SAUCE_VANILLA
import com.example.ice_cream.utilities.CHOCOLATE_CONE
import com.example.ice_cream.utilities.CUP
import com.example.ice_cream.utilities.NORMAL_CONE
import com.example.ice_cream.utilities.SWEET_CONE
import com.example.ice_cream.viewmodel.ItemOrderViewModel
import com.example.ice_cream.viewmodel.ItemOrderViewModelProvider

class SelectExtraActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySelectExtraBinding

    private lateinit var spinnerCones: Spinner
    private lateinit var spinnerOthers: Spinner
    private lateinit var spinnerDressings: Spinner

    private lateinit var confirmButton: Button
    private lateinit var iceCreamImage: ImageView
    private lateinit var iceCream: IceCream

    private lateinit var itemOrderViewModel: ItemOrderViewModel
    private lateinit var itemOrderCountText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectExtraBinding.inflate(layoutInflater)
        setContentView(binding.root)
        itemOrderViewModel = ItemOrderViewModelProvider.getItemOrderViewModel(this)
        itemOrderCountText = binding.headerBar.itemCountText

        itemOrderViewModel.allItemOrders.observe(this) {
            itemOrderViewModel.itemOrderCount = it.size
            itemOrderCountText.text = itemOrderViewModel.itemOrderCount.toString()
        }

        spinnerCones = binding.spinnerCones
        spinnerOthers = binding.spinnerOther
        spinnerDressings = binding.spinnerDressings
        confirmButton = binding.confirmButton
        iceCreamImage = binding.selectExtraImage


        iceCream = intent.getParcelableExtra(EXTRA_ICE_CREAM)!!
        Glide.with(this).load(iceCream.imageUrl).into(iceCreamImage)

        val toppings = arrayOf(NORMAL_CONE, SWEET_CONE, CHOCOLATE_CONE, CUP)
        val other = arrayOf(OTHER_NONE, OTHER_SUGAR_MAGIC, OTHER_ROLETTI, OTHER_WAFER)
        val sauces = arrayOf(SAUCE_NONE, SAUCE_STRAWBERRY, SAUCE_VANILLA)


        val toppingsAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, toppings)
        val otherAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, other)
        val saucesAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, sauces)

        spinnerCones.adapter = toppingsAdapter
        spinnerOthers.adapter = otherAdapter
        spinnerDressings.adapter = saucesAdapter

        confirmButton.setOnClickListener {
            val selectedConeId = when (spinnerCones.selectedItem.toString()) {
                NORMAL_CONE -> 1
                SWEET_CONE -> 2
                CHOCOLATE_CONE -> 3
                CUP -> 4
                else -> -1
            }
            val selectedOtherId = when (spinnerOthers.selectedItem.toString()) {
                OTHER_SUGAR_MAGIC -> 5
                OTHER_ROLETTI -> 6
                OTHER_WAFER -> 7
                else -> null
            }
            val selectedDressingId = when (spinnerDressings.selectedItem.toString()) {
                SAUCE_STRAWBERRY -> 8
                SAUCE_VANILLA -> 9
                else -> null
            }

            itemOrderViewModel.addItemOrder(
                ItemOrder(
                    0,
                    iceCream.id!!,
                    iceCream.name!!,
                    selectedConeId,
                    selectedDressingId,
                    selectedOtherId)
            )
            itemOrderViewModel.itemOrderCount++
            itemOrderCountText.text = itemOrderViewModel.itemOrderCount.toString()
        }
    }

    fun navigateToCart(view: View) {
        val intent = Intent(this, ShoppingCartActivity::class.java)
        startActivity(intent)
    }
}
