package com.example.ecommerce.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.model.Category
import com.example.ecommerce.model.Product
import com.example.ecommerce.repo.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor
    (private val repository: Repository): ViewModel(){
    fun fetchCategories(): MutableLiveData<List<Category>>{
        return repository.fetchCategories()
    }
    fun fetchProducts(categoryName: String): MutableLiveData<List<Product>>{
        return repository.fetchProducts(categoryName)
    }

    fun addToCart(cartItem: Product) = viewModelScope.launch {
        repository.addToCart(cartItem)
    }
    fun getCartItems(): MutableLiveData<List<Product>>{
        val cartItems = MutableLiveData<List<Product>>()
        viewModelScope.launch {
            val items = repository.getCartItems()
            cartItems.postValue(items)
        }
        return cartItems
    }
    fun removeFromCart(productId: String) = viewModelScope.launch {
        repository.removeFromCart(productId)
    }
    fun clearCart() = viewModelScope.launch {
        repository.clearCart()
    }
    fun savePurchasesInFirestore(product: Product){
        repository.savePurchaseInFirestore(product)
    }

}