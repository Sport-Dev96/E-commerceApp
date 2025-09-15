package com.example.ecommerce.util

import androidx.recyclerview.widget.DiffUtil
import com.example.ecommerce.model.Category

class CategoryDiffCallBack: DiffUtil.ItemCallback<Category>() {
    override fun areItemsTheSame(
        oldItem: Category,
        newItem: Category
    ): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(
        oldItem: Category,
        newItem: Category
    ): Boolean {
      return oldItem == newItem
    }
}