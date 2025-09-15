package com.example.ecommerce

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ecommerce.databinding.ActivityMainBinding
import com.example.ecommerce.util.CategoryAdapter
import com.example.ecommerce.viewmodel.MyViewModel
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ecommerce.view.CartActivity
import com.example.ecommerce.view.CategoryItems
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var categoryAdapter: CategoryAdapter
    private val viewModel: MyViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewCartButton.setOnClickListener{
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }
        categoryAdapter = CategoryAdapter { categoryName ->
           onCategoryClick(categoryName)
        }

        binding.recyclerView.adapter = categoryAdapter
        binding.recyclerView.layoutManager = GridLayoutManager(this,2)


        val result = viewModel.fetchCategories()
        result.observe(this) { newCategoryList ->
            if (newCategoryList.isNotEmpty()) {
                categoryAdapter.submitList(newCategoryList)
            } else {
                Toast.makeText(
                    this, "Something went wrong",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
    fun onCategoryClick(categoryName: String){
        val intent = Intent(this,
            CategoryItems::class.java)
        intent.putExtra("CATEGORY_NAME",categoryName)
        startActivity(intent)

        Toast.makeText(this,"Clicked:$categoryName", Toast.LENGTH_LONG).show()
    }

}



