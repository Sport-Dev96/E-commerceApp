package com.example.ecommerce.util

import androidx.recyclerview.widget.DiffUtil
import com.example.ecommerce.model.Product

class ProductDiffCallBack: DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(
        oldItem: Product,
        newItem: Product
    ): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(
        oldItem: Product,
        newItem: Product
    ): Boolean {
        return oldItem == newItem
    }
}