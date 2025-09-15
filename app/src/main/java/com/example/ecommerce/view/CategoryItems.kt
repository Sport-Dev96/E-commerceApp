package com.example.ecommerce.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerce.R
import com.example.ecommerce.databinding.ActivityCategoryItemsBinding
import com.example.ecommerce.model.Product
import com.example.ecommerce.util.ProductAdapter
import com.example.ecommerce.viewmodel.MyViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue
@AndroidEntryPoint
class CategoryItems : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryItemsBinding
    private lateinit var productAdapter: ProductAdapter
    private val viewModel: MyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
       binding = ActivityCategoryItemsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        productAdapter = ProductAdapter{selectedProduct->
            onProductClick(selectedProduct)
        }
        binding.recycleViewCategory.adapter=productAdapter
        binding.recycleViewCategory.layoutManager = LinearLayoutManager(this)

        val categoryName = intent.getStringExtra("CATEGORY_NAME") ?: ""

        val result = viewModel.fetchProducts(categoryName)
        result.observe(this){newProductsList->
            if (newProductsList.isNotEmpty()){
                productAdapter.submitList(newProductsList)
            }else{
                Log.v("TAGY","Error")
            }
        }




    }
    private fun onProductClick(selectedProduct: Product){
        val intent = Intent(this, ProductDetails::class.java)
        intent.putExtra("productTitle",selectedProduct.title)
        intent.putExtra("productPrice",selectedProduct.price)
        intent.putExtra("productImageUrl",selectedProduct.imageUrl)
        intent.putExtra("productID",selectedProduct.id)
        startActivity(intent)

    }
}