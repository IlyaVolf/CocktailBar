package com.example.cocktailbar.presentation.cocktail_details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.cocktailbar.R
import com.example.cocktailbar.databinding.FragmentCocktailDetailsBinding
import com.example.cocktailbar.domain.entities.Cocktail
import com.example.cocktailbar.domain.entities.Ingredient
import com.example.cocktailbar.presentation.list.IngredientsAdapter
import com.example.cocktailbar.presentation.my_cocktails.MyCocktailsState
import com.example.cocktailbar.utils.DataHolder
import com.example.cocktailbar.utils.createSimpleDialog
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
    private var cocktail: Cocktail? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cocktailRecipeLayout.ingredientsListRv.adapter = adapter
        initListeners()
        observeCocktail()
    }

    private fun observeCocktail() {
        viewModel.cocktail.observe(viewLifecycleOwner) { state ->
            when (state) {
                is CocktailDetailsState.Initial -> {
                    setCocktailDescriptionVisibility(false)
                    setLoadingVisibility(false)
                    setErrorVisibility(false)
                }

                is CocktailDetailsState.Loading -> {
                    setCocktailDescriptionVisibility(false)
                    setLoadingVisibility(true)
                    setErrorVisibility(false)
                }

                is CocktailDetailsState.DataReady -> {
                    setCocktailDescriptionVisibility(true)
                    setLoadingVisibility(false)
                    setErrorVisibility(false)
                    cocktail = state.cocktail
                    renderContent(state.cocktail)
                }

                is CocktailDetailsState.Error -> {
                    setCocktailDescriptionVisibility(false)
                    setLoadingVisibility(false)
                    setErrorVisibility(true)
                }

                CocktailDetailsState.DeletionReady -> {
                    findNavController().popBackStack()
                }
            }
        }
    }

    private fun setCocktailDescriptionVisibility(isVisible: Boolean) {
        with(binding) {
            cocktailRecipeLayout.root.isVisible = isVisible
            cocktailImageIv.isVisible = isVisible
        }
    }

    private fun setLoadingVisibility(isVisible: Boolean) {
        binding.loadingView.root.isVisible = isVisible
    }

    private fun setErrorVisibility(isVisible: Boolean) {
        binding.errorView.root.isVisible = isVisible
    }

    private fun renderContent(cocktail: Cocktail) {
        with(binding.cocktailRecipeLayout) {
            cocktailNameTv.text = cocktail.name
            binding.cocktailImageIv.loadImage(cocktail.image.orEmpty())

            if (cocktail.description == "") {
                cocktailDescriptionTv.isVisible = false
            } else {
                cocktailDescriptionTv.isVisible = true
                cocktailDescriptionTv.text = cocktail.description
            }

            if (cocktail.recipe == "") {
                recipeTitleTv.isVisible = false
                recipeTv.isVisible = false
                recipeSpace.isVisible = false
            } else {
                recipeTitleTv.isVisible = true
                recipeTv.isVisible = true
                recipeTv.text = cocktail.recipe
                recipeSpace.isVisible = true
            }
        }

        val ingredients = cocktail.ingredients.map { Ingredient(it.indexOf(it).toLong(), it) }
        adapter.submitList(ingredients)
    }

    private fun initListeners() {
        initErrorViewListener()
        initDeleteButtonListener()
        initEditButtonListener()
    }

    private fun initErrorViewListener() {
        binding.errorView.root.setOnClickListener {
            viewModel.tryAgain()
        }
    }

    private fun initDeleteButtonListener() {
        binding.cocktailRecipeLayout.deleteButton.setOnClickListener {
            createDialogDelete()
        }
    }

    private fun initEditButtonListener() {
        binding.cocktailRecipeLayout.editButton.setOnClickListener {
        }
    }

    private fun createDialogDelete() {
        val messageText = getString(R.string.ask_delete_cocktail_warning)

        val neutralButtonText = getString(R.string.action_back)
        val negativeButtonText = getString(R.string.action_delete)

        createSimpleDialog(
            requireContext(),
            null,
            messageText,
            negativeButtonText,
            { dialog, _ ->
                run {
                    viewModel.deleteCocktail(cocktail)
                    dialog.cancel()
                }
            },
            neutralButtonText,
            { dialog, _ ->
                run {
                    dialog.cancel()
                }
            },
            null,
            null,
        )
    }

}