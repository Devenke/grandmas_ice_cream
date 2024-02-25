package com.example.ice_cream.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ice_cream.model.ItemOrder

@Database(entities = [ItemOrder::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getItemOrderDao(): ItemOrderDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?:
        synchronized(LOCK) {
            instance ?: createDatabase(context).also {
                instance = it
            }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "item_order_db"
            ).build()
    }
}