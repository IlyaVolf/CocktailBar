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
import com.example.cocktailbar.utils.DataHolder
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
                    manageVisibility(
                        visibilityLoadingView = false,
                        visibilityEmptyListView = false,
                        visibilityListView = false,
                        visibilityErrorView = false
                    )
                }

                is MyCocktailsState.Loading -> {
                    manageVisibility(
                        visibilityLoadingView = true,
                        visibilityEmptyListView = false,
                        visibilityListView = false,
                        visibilityErrorView = false
                    )
                }

                is MyCocktailsState.Ready -> {
                    manageVisibility(
                        visibilityLoadingView = false,
                        visibilityEmptyListView = state.cocktailsList.isEmpty(),
                        visibilityListView = state.cocktailsList.isNotEmpty(),
                        visibilityErrorView = false
                    )
                    adapter.submitList(state.cocktailsList)
                }

                is MyCocktailsState.Error -> {
                    manageVisibility(
                        visibilityLoadingView = false,
                        visibilityEmptyListView = false,
                        visibilityListView = false,
                        visibilityErrorView = true
                    )
                }
            }
        }
    }

    private fun manageVisibility(
        visibilityLoadingView: Boolean,
        visibilityEmptyListView: Boolean,
        visibilityListView: Boolean,
        visibilityErrorView: Boolean
    ) {
        with (binding) {
            loadingView.root.isVisible = visibilityLoadingView
            emptyListCocktailsCl.root.isVisible = visibilityEmptyListView
            myCocktailsTv.isVisible = visibilityListView
            cocktailsListRv.isVisible = visibilityListView
            errorView.root.isVisible = visibilityErrorView
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