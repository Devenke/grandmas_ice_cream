package com.example.ice_cream.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ice_cream.R
import com.example.ice_cream.model.Extra

class ExtrasAdapter(var extras: List<Extra>, private val context: Context) : RecyclerView.Adapter<ExtrasAdapter.ExtrasViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExtrasViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.extra_item, parent, false)
        return ExtrasViewHolder(view)
    }

    override fun getItemCount() = extras.size

    override fun onBindViewHolder(holder: ExtrasViewHolder, position: Int) {
        holder.bindExtra(extras[position], context)
    }

    inner class ExtrasViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val extraType: TextView = itemView.findViewById(R.id.extraType)
        val spinner: Spinner = itemView.findViewById(R.id.extraSpinner)

        fun bindExtra(extra: Extra, context: Context) {
            extraType.text = extra.type

            val items = extra.items.map { it.name }.toMutableList()
            if (extra.required != true) {
                items.add(0, "")
                spinner.setSelection(0, false)
            }
            val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, items)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
    }
}
