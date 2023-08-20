package com.example.cocktailbar.presentation.cocktail_details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktailbar.databinding.ItemIngredientBinding
import com.example.cocktailbar.domain.entities.Ingredient

class IngredientsAdapter : ListAdapter<Ingredient, IngredientsAdapter.IngredientHolder>(
    ItemCallback
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemIngredientBinding.inflate(inflater, parent, false)

        return IngredientHolder(binding)
    }

    override fun onBindViewHolder(holder: IngredientHolder, position: Int) {
        val ingredient = getItem(position)

        with(holder.binding) {
            root.tag = ingredient
            ingredientTv.text = ingredient.description
        }
    }

    class IngredientHolder(
        val binding: ItemIngredientBinding
    ) : RecyclerView.ViewHolder(binding.root)

    object ItemCallback : DiffUtil.ItemCallback<Ingredient>() {
        override fun areItemsTheSame(oldItem: Ingredient, newItem: Ingredient): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Ingredient, newItem: Ingredient): Boolean {
            return oldItem == newItem
        }
    }
}
