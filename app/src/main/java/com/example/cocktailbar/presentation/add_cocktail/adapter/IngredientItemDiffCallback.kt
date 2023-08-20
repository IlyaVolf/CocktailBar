package com.example.cocktailbar.presentation.add_cocktail.adapter

import androidx.recyclerview.widget.DiffUtil

class IngredientItemDiffCallback : DiffUtil.ItemCallback<IngredientItem>() {
    override fun areItemsTheSame(oldItem: IngredientItem, newItem: IngredientItem): Boolean =
        oldItem === newItem

    override fun areContentsTheSame(oldItem: IngredientItem, newItem: IngredientItem): Boolean =
        oldItem == newItem
}