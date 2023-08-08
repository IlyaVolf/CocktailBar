package com.example.cocktailbar.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
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
    //private val viewModel by viewModels<MyCocktailsViewModel>()
    private val binding by viewBinding<FragmentMyCocktailsBinding>()

    private val adapter = CocktailsAdapter(object : CocktailsAdapter.Listener {
        override fun onPressCocktail(cocktail: Cocktail) {
            navigateToCocktailDetailsFragment(cocktail.id)
        }
    })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeCocktails()
    }

    private fun observeCocktails() {
        viewModel.cocktailsList.observe(viewLifecycleOwner) { holder ->
            when (holder) {
                is DataHolder.READY -> {
                    setVisibility(
                        isRefreshing = false,
                        visibilityLoadingView = View.GONE,
                        visibilityContentView = View.VISIBLE,
                        visibilityErrorView = View.GONE
                    )
                    adapter.submitList(holder.data)
                    if (holder.data.isNotEmpty()) {
                        binding.cocktailsListRv.visibility = View.VISIBLE
                        binding.emptyListCocktailsCl.root.visibility = View.GONE
                    } else {
                        binding.cocktailsListRv.visibility = View.GONE
                        binding.emptyListCocktailsCl.root.visibility = View.VISIBLE
                    }
                }

                else -> {
                    setVisibility(
                        isRefreshing = false,
                        visibilityLoadingView = View.GONE,
                        visibilityContentView = View.VISIBLE,
                        visibilityErrorView = View.GONE
                    )
                }
            }
        }
    }

    private fun navigateToCocktailDetailsFragment(cocktailId: Long) {
        val direction =
            MyCocktailsFragmentDirections.actionMyCocktailsFragmentToCocktailDetailsFragment(
                cocktailId = cocktailId
            )
        findNavController().navigate(direction)
    }

    private fun setVisibility(
        isRefreshing: Boolean,
        visibilityLoadingView: Int,
        visibilityContentView: Int,
        visibilityErrorView: Int
    ) {
        /*binding.swipeRefreshLayout.isRefreshing = isRefreshing
        binding.loadingView.root.visibility = visibilityLoadingView
        binding.contentView.visibility = visibilityContentView
        binding.errorView.root.visibility = visibilityErrorView*/
    }

}