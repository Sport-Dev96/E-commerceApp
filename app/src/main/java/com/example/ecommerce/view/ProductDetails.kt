package com.example.ecommerce.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.ecommerce.R
import com.example.ecommerce.databinding.ActivityProductDetailsBinding
import com.example.ecommerce.model.Product
import com.example.ecommerce.viewmodel.MyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetails : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailsBinding
    private val viewModel: MyViewModel by viewModels ()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val productTitle = intent.getStringExtra("productTitle") ?: ""
        val productPrice = intent.getDoubleExtra("productPrice",0.0)
        val productImageUrl = intent.getStringExtra("productImageUrl") ?: ""
        val productID = intent.getStringExtra("productID") ?: "0"


        binding.productTitleDetail.text = productTitle
        binding.productPriceDetail.text = "$ $productPrice"

        Glide.with(this)
            .load(productImageUrl)
            .into(binding.productImageDetail)

        binding.addToCartButton.setOnClickListener {
            addToCart(Product(productID, productTitle,productPrice,productImageUrl))
        }

    }
    fun addToCart(product: Product){
       viewModel.addToCart(product)
        Toast.makeText(this,"Added to cart", Toast.LENGTH_LONG).show()
    }
}