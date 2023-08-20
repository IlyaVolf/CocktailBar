package com.example.cocktailbar.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import com.example.cocktailbar.R
import com.example.cocktailbar.databinding.FragmentCocktailDetailsBinding
import com.example.cocktailbar.domain.entities.Cocktail
import com.example.cocktailbar.domain.entities.Ingredient
import com.example.cocktailbar.presentation.list.IngredientsAdapter
import com.example.cocktailbar.utils.DataHolder
import com.example.cocktailbar.utils.image_loader.loadImage
import com.example.cocktailbar.utils.viewBinding
import com.example.cocktailbar.utils.viewModelCreator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CocktailDetailsFragment : Fragment(R.layout.fragment_cocktail_details) {

    private val args by navArgs<CocktailDetailsFragmentArgs>()

    @Inject
    lateinit var factory: CocktailDetailsViewModel.Factory
    private val viewModel by viewModelCreator {
        factory.create(args.cocktailId)
    }

    private val binding by viewBinding<FragmentCocktailDetailsBinding>()

    private val adapter = IngredientsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ingredientsListRv.adapter = adapter
        initListeners()
        observeCocktail()
    }

    private fun observeCocktail() {
        viewModel.cocktail.observe(viewLifecycleOwner) { holder ->
            when (holder) {
                is DataHolder.LOADING -> {
                    manageVisibility(
                        visibilityLoadingView = true,
                        visibilityContentView = false,
                        visibilityErrorView = false
                    )
                }

                is DataHolder.READY -> {

                    manageVisibility(
                        visibilityLoadingView = false,
                        visibilityContentView = true,
                        visibilityErrorView = false
                    )
                    renderContent(holder.data)
                }

                is DataHolder.ERROR -> {
                    manageVisibility(
                        visibilityLoadingView = false,
                        visibilityContentView = false,
                        visibilityErrorView = true
                    )
                }

                else -> {
                    manageVisibility(
                        visibilityLoadingView = false,
                        visibilityContentView = false,
                        visibilityErrorView = false
                    )
                }
            }
        }
    }

    private fun manageVisibility(
        visibilityLoadingView: Boolean,
        visibilityContentView: Boolean,
        visibilityErrorView: Boolean
    ) {
        with(binding) {
            loadingView.root.isVisible = visibilityLoadingView
            cocktailNameTv.isVisible = visibilityContentView
            cocktailDescriptionTv.isVisible = visibilityContentView
            ingredientsListRv.isVisible = visibilityContentView
            cocktailImageIv.isVisible = visibilityContentView
            errorView.root.isVisible = visibilityErrorView
        }
    }

    private fun renderContent(cocktail: Cocktail) {
        val ingredients = mutableListOf<Ingredient>()
        for (i in cocktail.ingredients.indices) {
            ingredients.add(Ingredient(i.toLong(), cocktail.ingredients[i]))
        }

        binding.cocktailNameTv.text = cocktail.name
        binding.cocktailImageIv.loadImage(cocktail.image.orEmpty())

        if (cocktail.description == "") {
            binding.cocktailDescriptionTv.isVisible = false
        } else {
            binding.cocktailDescriptionTv.isVisible = true
            binding.cocktailDescriptionTv.text = cocktail.description
        }

        if (cocktail.recipe == "") {
            binding.recipeTitleTv.isVisible = false
            binding.recipeTv.isVisible = false
        } else {
            binding.recipeTitleTv.isVisible = true
            binding.recipeTv.isVisible = true
            binding.recipeTv.text = cocktail.recipe
        }

        adapter.submitList(ingredients)
    }

    private fun initListeners() {
        initErrorViewListener()
        initEditButtonListener()
    }

    private fun initErrorViewListener() {
        binding.errorView.root.setOnClickListener {
            viewModel.tryAgain()
        }
    }

    private fun initEditButtonListener() {
        binding.editButton.setOnClickListener {
        }
    }

}