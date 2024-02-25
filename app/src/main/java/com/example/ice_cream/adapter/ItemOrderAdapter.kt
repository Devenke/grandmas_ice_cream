package com.example.ice_cream.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ice_cream.R
import com.example.ice_cream.SelectExtraActivity
import com.example.ice_cream.databinding.ItemOrderBinding
import com.example.ice_cream.enums.Status
import com.example.ice_cream.model.IceCream
import com.example.ice_cream.model.ItemOrder
import com.example.ice_cream.utilities.BASE_PRICE
import com.example.ice_cream.utilities.EXTRA_ICE_CREAM
import com.example.ice_cream.utilities.STATUS_MELTED
import com.example.ice_cream.utilities.STATUS_NULL
import com.example.ice_cream.utilities.STATUS_UNAVAILABLE
import com.example.ice_cream.viewmodel.ItemOrderViewModel

class ItemOrderAdapter(private val itemOrders: List<ItemOrder>, private val context: Context) : RecyclerView.Adapter<ItemOrderAdapter.ItemOrderViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemOrderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
        return ItemOrderViewHolder(view)
    }

    override fun getItemCount() = itemOrders.size

    override fun onBindViewHolder(holder: ItemOrderViewHolder, position: Int) {
        holder.bindProduct(itemOrders[position], context)
    }

    inner class ItemOrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tasteType: TextView = itemView.findViewById(R.id.itemOrderTasteText)
        fun bindProduct(itemOrder: ItemOrder, context: Context) {

            tasteType.text = itemOrder.iceCreamName
        }
    }
}