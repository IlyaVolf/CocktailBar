package com.example.cocktailbar.presentation.my_cocktails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.example.cocktailbar.R
import com.example.cocktailbar.databinding.FragmentMyCocktailsBinding
import com.example.cocktailbar.domain.entities.Cocktail
import com.example.cocktailbar.presentation.list.CocktailsAdapter
import com.example.cocktailbar.utils.viewBinding
import com.example.cocktailbar.utils.viewModelCreator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MyCocktailsFragment : Fragment(R.layout.fragment_my_cocktails) {

    @Inject
    lateinit var factory: MyCocktailsViewModel.Factory
    private val viewModel by viewModelCreator {
        factory.create()
    }

    private val binding by viewBinding<FragmentMyCocktailsBinding>()

    private val adapter = CocktailsAdapter(object : CocktailsAdapter.Listener {
        override fun onPressCocktail(cocktail: Cocktail) {
            navigateToCocktailDetailsFragment(cocktail.id)
        }
    })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cocktailsListRv.adapter = adapter
        initListeners()
        observeCocktails()
    }

    private fun observeCocktails() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is MyCocktailsState.Initial -> {
                    setEmptyListVisibility(false)
                    setNonEmptyListVisibility(false)
                    setLoadingVisibility(false)
                    setErrorVisibility(false)
                }

                is MyCocktailsState.Loading -> {
                    setEmptyListVisibility(false)
                    setNonEmptyListVisibility(false)
                    setLoadingVisibility(true)
                    setErrorVisibility(false)
                }

                is MyCocktailsState.Ready -> {
                    setEmptyListVisibility(state.cocktailsList.isEmpty())
                    setNonEmptyListVisibility(state.cocktailsList.isNotEmpty())
                    setLoadingVisibility(false)
                    setErrorVisibility(false)

                    adapter.submitList(state.cocktailsList)
                }

                is MyCocktailsState.Error -> {
                    setEmptyListVisibility(false)
                    setNonEmptyListVisibility(false)
                    setLoadingVisibility(false)
                    setErrorVisibility(true)
                }
            }
        }
    }

    private fun setLoadingVisibility(isVisible: Boolean) {
        binding.loadingView.root.isVisible = isVisible
    }

    private fun setErrorVisibility(isVisible: Boolean) {
        binding.errorView.root.isVisible = isVisible
    }
    private fun setNonEmptyListVisibility(isVisible: Boolean) {
        with (binding) {
            cocktailsListRv.isVisible = isVisible
            myCocktailsTv.isVisible = isVisible
        }
    }
    private fun setEmptyListVisibility(isVisible: Boolean) {
        with (binding) {
            emptyListCocktailsV.root.isVisible = isVisible
            firstCocktailHintV.root.isVisible = isVisible
        }
    }

    private fun initListeners() {
        initErrorViewListener()
        initAddCocktailListener()
    }

    private fun initErrorViewListener() {
        binding.errorView.root.setOnClickListener {
            viewModel.tryAgain()
        }
    }

    private fun initAddCocktailListener() {
        binding.addCocktailB.setOnClickListener {
            navigateToAddCocktailFragment()
        }
    }

    private fun navigateToCocktailDetailsFragment(cocktailId: Long) {
        val direction =
            MyCocktailsFragmentDirections.actionMyCocktailsFragmentToCocktailDetailsFragment(
                cocktailId = cocktailId
            )
        findNavController().navigate(direction)
    }

    private fun navigateToAddCocktailFragment() {
        val direction =
            MyCocktailsFragmentDirections.actionMyCocktailsFragmentToAddCocktailFragment()
        findNavController().navigate(direction)
    }

}