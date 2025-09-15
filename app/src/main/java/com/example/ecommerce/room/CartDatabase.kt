package com.example.ecommerce.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ecommerce.model.Product

@Database(entities = [Product::class], version = 1, exportSchema = false)
abstract class CartDatabase: RoomDatabase() {
    abstract fun cartDAO(): CartDAO
    companion object{
        @Volatile
        private var INSTANCE: CartDatabase?=null
        
        fun getDatabase(context: Context): Any{ // Also changed return type to CartDatabase
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CartDatabase::class.java,
                    "cart_database"
                    
                ).build()
                INSTANCE = instance // Don't forget to assign the instance
                instance // and return it
            }
        }
    }
}