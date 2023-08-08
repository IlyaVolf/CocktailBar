package com.example.cocktailbar.presentation.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktailbar.databinding.ItemCocktailBinding
import com.example.cocktailbar.domain.entities.Cocktail
import com.example.cocktailbar.utils.image_loader.loadImage

class CocktailsAdapter(
    private val listener: Listener
) : ListAdapter<Cocktail, CocktailsAdapter.CocktailHolder>(ItemCallback), View.OnClickListener {

    override fun onClick(v: View) {
        val cocktail = v.tag as Cocktail
        listener.onPressCocktail(cocktail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CocktailHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCocktailBinding.inflate(inflater, parent, false)

        binding.root.setOnClickListener(this)

        return CocktailHolder(binding)
    }

    override fun onBindViewHolder(holder: CocktailHolder, position: Int) {
        val cocktail = getItem(position)

        with(holder.binding) {
            root.tag = cocktail
            cocktailNameTv.text = cocktail.name
            cocktailImageIv.loadImage(cocktail.image.orEmpty())
        }
    }

    interface Listener {
        fun onPressCocktail(cocktail: Cocktail)
    }

    class CocktailHolder(
        val binding: ItemCocktailBinding
    ) : RecyclerView.ViewHolder(binding.root)

    object ItemCallback : DiffUtil.ItemCallback<Cocktail>() {
        override fun areItemsTheSame(oldItem: Cocktail, newItem: Cocktail): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Cocktail, newItem: Cocktail): Boolean {
            return oldItem == newItem
        }
    }
}
