package com.example.cocktailbar.presentation.add_cocktail

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cocktailbar.R
import com.example.cocktailbar.databinding.FragmentAddEditCocktailBinding
import com.example.cocktailbar.domain.entities.AddCocktailData
import com.example.cocktailbar.domain.entities.Cocktail
import com.example.cocktailbar.presentation.add_cocktail.adapter.IngredientItem
import com.example.cocktailbar.presentation.add_cocktail.adapter.IngredientsListAdapter
import com.example.cocktailbar.utils.createSimpleDialog
import com.example.cocktailbar.utils.viewBinding
import com.example.cocktailbar.utils.viewModelCreator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class AddEditCocktailFragment : Fragment(R.layout.fragment_add_edit_cocktail) {

    private val args by navArgs<AddEditCocktailFragmentArgs>()

    @Inject
    lateinit var factory: AddEditCocktailViewModel.Factory
    private val viewModel by viewModelCreator {
        factory.create(args.cocktailid)
    }

    private val binding by viewBinding<FragmentAddEditCocktailBinding>()

    private val ingredientsListAdapter = IngredientsListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    createCancelDialog()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initIngredientsRecyclerView()
        initListeners()
        observeSave()
    }

    private fun initListeners() {
        initSaveButtonListener()
        initCancelButtonListener()
        initErrorViewListener()
    }

    private fun observeSave() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is AddEditCocktailState.Initial -> {
                    setContentVisibility(false)
                    setLoadingVisibility(false)
                    setErrorVisibility(false)
                }

                is AddEditCocktailState.InProcess -> {
                    setContentVisibility(false)
                    setLoadingVisibility(true)
                    setErrorVisibility(false)
                }

                is AddEditCocktailState.DataReady -> {
                    setContentVisibility(true)
                    setLoadingVisibility(false)
                    setErrorVisibility(false)
                    renderContent(state.cocktail)
                }

                is AddEditCocktailState.ValidationError -> {
                    binding.nameEt.error =
                        if (state.isNameBlank) getString(R.string.add_cocktail_name) else null

                    if (state.areIngredientsEmpty) {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.add_ingredients), Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                is AddEditCocktailState.SaveSuccessful -> {
                    goBack()
                }

                is AddEditCocktailState.Error -> {
                    setContentVisibility(false)
                    setLoadingVisibility(false)
                    setErrorVisibility(true)
                    binding.errorView.Error.text = state.error.toString()
                }
            }
        }
    }

    private fun setContentVisibility(isVisible: Boolean) {
        with(binding) {
            cancelButton.isVisible = isVisible
            saveButton.isVisible = isVisible
            contentNsv.isVisible = isVisible
        }
    }

    private fun initIngredientsRecyclerView() {
        ingredientsListAdapter.submitList(listOf(IngredientItem("2 glasses of water")))
        ingredientsListAdapter.onIngredientClick = { item ->
            val newList = ingredientsListAdapter.currentList.toList().filter { it != item }
            ingredientsListAdapter.submitList(newList)
        }
        ingredientsListAdapter.onAddButtonClick = {
            showIngredientDialog()
        }

        with(binding.recyclerViewIngredients) {
            adapter = ingredientsListAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

    }

    private fun setLoadingVisibility(isVisible: Boolean) {
        binding.loadingView.root.isVisible = isVisible
    }

    private fun setErrorVisibility(isVisible: Boolean) {
        binding.errorView.root.isVisible = isVisible
    }

    private fun renderContent(cocktail: Cocktail?) {
        if (cocktail == null) return

        with(binding) {
            nameEt.setText(cocktail.name)
            descriptionEt.setText(cocktail.description)
            recipeEt.setText(cocktail.recipe)
            val ingredientsItems = cocktail.ingredients.map { IngredientItem(it) }
            ingredientsListAdapter.submitList(ingredientsItems)
        }
    }

    private fun showIngredientDialog() {
        val dialogFragment = IngredientDialogFragment()
        initIngredientDialogResultListener()
        dialogFragment.show(childFragmentManager, null)
    }

    private fun initIngredientDialogResultListener() {
        childFragmentManager.setFragmentResultListener(
            IngredientDialogFragment.INGREDIENT_REQUEST_CODE,
            viewLifecycleOwner
        ) { _, data ->
            val ingredientString = data.getString(IngredientDialogFragment.INGREDIENT)
            if (ingredientString != null) {
                val newList = ingredientsListAdapter.currentList.toMutableList()
                newList.add(IngredientItem(ingredientString))
                ingredientsListAdapter.submitList(newList)
            }
        }
    }

    private fun initSaveButtonListener() {
        binding.saveButton.setOnClickListener {
            val addCocktailData = AddCocktailData(
                name = binding.nameEt.text.toString(),
                description = binding.descriptionEt.text.toString(),
                recipe = binding.recipeEt.text.toString(),
                ingredients = getIngredients(),
                image = getImage()
            )
            viewModel.save(addCocktailData)
        }
    }

    private fun getIngredients(): List<String> {
        return ingredientsListAdapter.currentList.map { it.description }
    }

    // TODO implement getPicture()
    private fun getImage(): String {
        return Uri.parse("android.resource://${requireContext().packageName}/drawable/banana_heaven")
            .toString()
    }

    private fun initCancelButtonListener() {
        binding.cancelButton.setOnClickListener {
            createCancelDialog()
        }
    }

    private fun initErrorViewListener() {
        binding.errorView.root.setOnClickListener {
            viewModel.tryAgain()
        }
    }

    private fun goBack() {
        findNavController().navigateUp()
    }

    private fun createCancelDialog() {
        val messageText = getString(R.string.ask_cancel_post_warning)

        val neutralButtonText = getString(R.string.action_back)

        val negativeButtonText = getString(R.string.action_delete)

        createSimpleDialog(
            context = requireContext(),
            titleText = null,
            messageText = messageText,
            negativeButtonText = negativeButtonText,
            negativeButtonAction = { dialog, _ ->
                run {
                    goBack()
                    dialog.cancel()
                }
            },
            neutralButtonText = neutralButtonText,
            neutralButtonAction = { dialog, _ ->
                run {
                    dialog.cancel()
                }
            },
            positiveButtonText = null,
            positiveButtonAction = null,
        )
    }

}