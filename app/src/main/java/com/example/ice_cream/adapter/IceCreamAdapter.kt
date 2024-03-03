package com.example.ice_cream.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ice_cream.R
import com.example.ice_cream.SelectExtraActivity
import com.example.ice_cream.enums.Status
import com.example.ice_cream.model.IceCream
import com.example.ice_cream.utilities.BASE_PRICE
import com.example.ice_cream.utilities.EXTRA_ICE_CREAM
import com.example.ice_cream.utilities.MISSING_IMAGE_URL
import com.example.ice_cream.utilities.STATUS_MELTED
import com.example.ice_cream.utilities.STATUS_NULL
import com.example.ice_cream.utilities.STATUS_UNAVAILABLE

class IceCreamAdapter(var iceCreams: List<IceCream>, private val context: Context) : RecyclerView.Adapter<IceCreamAdapter.IceCreamViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IceCreamViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.ice_cream_item, parent, false)
        return IceCreamViewHolder(view)
    }

    override fun getItemCount() = iceCreams.size

    override fun onBindViewHolder(holder: IceCreamViewHolder, position: Int) {
        holder.bindProduct(iceCreams[position], context)
    }

    inner class IceCreamViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tasteType: TextView = itemView.findViewById(R.id.itemTasteText)
        private val price: TextView = itemView.findViewById(R.id.itemPriceText)
        private val itemImage: ImageView = itemView.findViewById(R.id.iceCreamImage)
        private val selectButton: Button = itemView.findViewById(R.id.selectButton)

        fun bindProduct(iceCream: IceCream, context: Context) {
            if (iceCream.imageUrl == null) iceCream.imageUrl = MISSING_IMAGE_URL
            Glide.with(context)
                .load(iceCream.imageUrl)
                .into(itemImage)

            tasteType.text = iceCream.name
            price.text = when (iceCream.status) {
                Status.AVAILABLE -> BASE_PRICE
                Status.MELTED -> STATUS_MELTED
                Status.UNAVAILABLE -> STATUS_UNAVAILABLE
                null -> STATUS_NULL
            }

            if (iceCream.status != Status.AVAILABLE) selectButton.isEnabled = false

            selectButton.setOnClickListener {
                val intent = Intent(context, SelectExtraActivity::class.java)
                intent.putExtra(EXTRA_ICE_CREAM, iceCream)
                context.startActivity(intent)
            }
        }
    }
}
