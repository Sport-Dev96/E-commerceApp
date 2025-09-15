package com.example.ecommerce.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.ecommerce.model.Product
import dagger.Provides


@Dao
interface CartDAO {
    @Insert
    suspend fun addToCart(cartItem: Product)

    @Query ("SELECT*FROM cart_items")
    suspend fun getCartItems(): List<Product>

    @Query("DELETE FROM cart_items WHERE id = :productId")
    suspend fun removeFromCart(productId: String)

    @Query("DELETE FROM cart_items")
    suspend fun clearCart()

}