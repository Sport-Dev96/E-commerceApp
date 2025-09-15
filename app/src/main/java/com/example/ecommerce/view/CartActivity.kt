package com.example.ecommerce.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerce.R
import com.example.ecommerce.databinding.ActivityCartBinding
import com.example.ecommerce.model.Product
import com.example.ecommerce.util.CartAdapter
import com.example.ecommerce.viewmodel.MyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    private val viewModel: MyViewModel by viewModels()
    private lateinit var cartAdapter: CartAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)


        cartAdapter = CartAdapter() { cartItem ->
            removeCartItem(cartItem)
        }


        binding.recycleViewCart.apply {
            adapter = cartAdapter
            layoutManager = LinearLayoutManager(this@CartActivity)
        }
        binding.clearCartButton.setOnClickListener {
            viewModel.clearCart()
            cartAdapter.submitList(emptyList())
        }
        binding.checkoutButton.setOnClickListener {
            //Handle checkout logic
            checkOutCart()
        }
        viewModel.getCartItems()
        viewModel.getCartItems().observe(this) { cartItems ->
            cartAdapter.submitList(cartItems)
        }

    }

    private fun removeCartItem(cartItem: Product) {
        viewModel.removeFromCart(cartItem.id)
        val updateCartItems = viewModel.getCartItems()
            .value?.toMutableList()
        cartAdapter.submitList(updateCartItems)
    }

    private fun checkOutCart() {
        viewModel.getCartItems().observe(this) { purchasedItems ->
            for (item in purchasedItems) {
                viewModel.savePurchasesInFirestore(item)
            }

        }
    }
}