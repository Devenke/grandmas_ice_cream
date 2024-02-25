package com.example.ice_cream

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import com.example.ice_cream.databinding.ActivitySelectExtraBinding

class SelectExtraActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySelectExtraBinding
    private lateinit var spinnerCones: Spinner
    private lateinit var spinnerOthers: Spinner
    private lateinit var spinnerdressings: Spinner
    private lateinit var confirmButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_extra)

        spinnerCones = findViewById(R.id.spinnerCones)
        spinnerOthers = findViewById(R.id.spinnerOther)
        spinnerdressings = findViewById(R.id.spinnerDressings)
        spinnerCones.prompt = "Válassz"
        confirmButton = findViewById(R.id.confirmButton)

        val toppings = arrayOf("Normál tölcsér", "Édes tölcsér", "Csokis tölcsér", "Kehely")
        val other = arrayOf("Nem kérek", "Cukorvarázs", "Roletti", "Ostya")
        val sauces = arrayOf("Nem kérek", "Eper öntet", "Vanília öntet")

        val toppingsAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, toppings)
        val otherAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, other)
        val saucesAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, sauces)

        spinnerCones.adapter = toppingsAdapter
        spinnerOthers.adapter = otherAdapter
        spinnerdressings.adapter = saucesAdapter

        confirmButton.setOnClickListener {
            val selectedTopping = spinnerCones.selectedItem.toString()
            val selectedOther = spinnerOthers.selectedItem.toString()
            val selectedSauce = spinnerdressings.selectedItem.toString()
        }
    }
}