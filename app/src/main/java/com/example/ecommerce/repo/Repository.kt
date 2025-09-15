package com.example.ecommerce.repo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.ecommerce.model.Category
import com.example.ecommerce.model.Product
import com.example.ecommerce.room.CartDAO
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class Repository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val cartDAO: CartDAO
) {
    fun fetchCategories(): MutableLiveData<List<Category>>{
        var categoryList = MutableLiveData<List<Category>>()
        val catImages = mapOf(
            "Electronics" to com.example.ecommerce.R.drawable.electronics,
            "Jewelry" to com.example.ecommerce.R.drawable.jewelery,
            "mensclothing" to com.example.ecommerce.R.drawable.men_clothing,
            "womenclothing" to com.example.ecommerce.R.drawable.women_clothing,
            "Cars" to com.example.ecommerce.R.drawable.car

        )
        firestore.collection("categories")
            .get()
            .addOnSuccessListener { documents->
                val category = documents.map { document->

                    val imageRes = catImages[document.id] ?:
                    com.example.ecommerce.R.drawable.ic_launcher_background

                    Category(name =  document.id,
                    catImg = imageRes)

                }
                categoryList.postValue(category)
                Log.v("TAGY","categoty:$categoryList")
            }
        return categoryList

    }

    fun fetchProducts(categoryName: String): MutableLiveData<List<Product>>{
        var productsList = MutableLiveData<List<Product>>()
        firestore.collection("categories")
            .document(categoryName)
            .collection("products")
            .get()
            .addOnSuccessListener { documents->
                val products = documents.map { document->
                    Product(
                        id = document.id,
                        title =  document.getString("title") ?: "",
                        price =  document.getDouble("price") ?: 0.0,
                        imageUrl = document.getString("imageUrl") ?: ""
                    )
                }
                productsList.postValue(products)
                Log.v("TAGY","product:$productsList")
            }
            .addOnFailureListener { exception->
                Log.v("TAGY","Exception:$exception")
            }
        return productsList
    }
    suspend fun addToCart(product: Product){
        cartDAO.addToCart(product)
    }
    suspend fun getCartItems(): List<Product>{
        return cartDAO.getCartItems()
    }
    suspend fun removeFromCart(productId: String){
        cartDAO.removeFromCart(productId)
    }
    suspend fun clearCart(){
        cartDAO.clearCart()
    }
    fun savePurchaseInFirestore(product: Product){
        firestore.collection("purchases")
            .add(product)
            .addOnSuccessListener {
                CoroutineScope(Dispatchers.IO).launch {
                    clearCart()
                }
            }
    }
}