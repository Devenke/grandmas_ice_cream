package com.example.ice_cream.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ice_cream.R
import com.example.ice_cream.model.ItemOrder

class ItemOrderAdapter(private val itemOrders: List<ItemOrder>, private val context: Context, private val iconClick: (ItemOrder) -> Unit) : RecyclerView.Adapter<ItemOrderAdapter.ItemOrderViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemOrderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
        return ItemOrderViewHolder(view, iconClick)
    }

    override fun getItemCount() = itemOrders.size

    override fun onBindViewHolder(holder: ItemOrderViewHolder, position: Int) {
        holder.bindProduct(itemOrders[position], context)
    }

    inner class ItemOrderViewHolder(itemView: View, val iconClick: (ItemOrder) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val tasteType: TextView = itemView.findViewById(R.id.itemOrderTasteText)
        private val deleteIcon: ImageView = itemView.findViewById(R.id.deleteItemOrderIcon)
        fun bindProduct(itemOrder: ItemOrder, context: Context) {
            tasteType.text = itemOrder.iceCreamName
            deleteIcon.setOnClickListener { iconClick(itemOrder) }
        }
    }
}