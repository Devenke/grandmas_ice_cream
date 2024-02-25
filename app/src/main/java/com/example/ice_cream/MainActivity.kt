package com.example.ice_cream

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ice_cream.adapter.IceCreamAdapter
import com.example.ice_cream.databinding.ActivityMainBinding
import com.example.ice_cream.enums.Status
import com.example.ice_cream.model.IceCream
import com.example.ice_cream.utilities.ICE_CREAMS_DATA_CHILD_PATH
import com.example.ice_cream.utilities.ICE_CREAMS_DATA_PATH
import com.example.ice_cream.utilities.NOTIFICATION_OUT_OF_ICE_CREAM
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var adapter: IceCreamAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseReference = FirebaseDatabase.getInstance().getReference(ICE_CREAMS_DATA_PATH)

        recyclerView = binding.mainRecycleView
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = IceCreamAdapter(emptyList(), this)
        getAllIceCreams()
        recyclerView.adapter = adapter

    }

    private fun getAllIceCreams() {
        val iceCreamList = mutableListOf<IceCream>()
        databaseReference = FirebaseDatabase.getInstance().getReference(ICE_CREAMS_DATA_PATH)
        databaseReference.child(ICE_CREAMS_DATA_CHILD_PATH).get().addOnSuccessListener { dataSnapshot ->
            if (dataSnapshot.exists()) {
                dataSnapshot.children.forEach { snapshot ->
                    snapshot.getValue(IceCream::class.java)?.let { iceCreamList.add(it) }
                }
                adapter.iceCreams = iceCreamList
                adapter.notifyDataSetChanged()
            } else {
                Toast.makeText(this, NOTIFICATION_OUT_OF_ICE_CREAM, Toast.LENGTH_SHORT).show()
            }
        }
    }
}