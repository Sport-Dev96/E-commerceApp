package com.example.ecommerce.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecommerce.databinding.ItemProductBinding
import com.example.ecommerce.model.Product

class ProductAdapter(
    private val onProductClick:(Product) ->Unit
): ListAdapter<Product, ProductAdapter.ProductViewHolder>
    (ProductDiffCallBack()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context),
            parent,false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ProductViewHolder,
        position: Int
    ) {
        val product = getItem(position)
        holder.binding.productTitle.text = product.title
        holder.binding.productPrice.text = product.price.toString()

        Glide.with(holder.itemView.context)
            .load(product.imageUrl)
            .into(holder.productImg)
        holder.itemView.setOnClickListener {
            onProductClick(product)
        }
    }

    class ProductViewHolder(val binding: ItemProductBinding): RecyclerView.ViewHolder(binding.root){
     val productImg = binding.productImage
    }
}