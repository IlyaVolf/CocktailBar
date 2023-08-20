package com.example.cocktailbar.presentation.add_cocktail

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cocktailbar.R
import com.example.cocktailbar.databinding.FragmentAddCocktailBinding
import com.example.cocktailbar.domain.entities.AddCocktailData
import com.example.cocktailbar.presentation.add_cocktail.adapter.IngredientItem
import com.example.cocktailbar.presentation.add_cocktail.adapter.IngredientsListAdapter
import com.example.cocktailbar.utils.createSimpleDialog
import com.example.cocktailbar.utils.viewBinding
import com.example.cocktailbar.utils.viewModelCreator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class AddCocktailFragment : Fragment(R.layout.fragment_add_cocktail) {

    @Inject
    lateinit var factory: AddCocktailViewModel.Factory
    private val viewModel by viewModelCreator {
        factory.create()
    }

    private val binding by viewBinding<FragmentAddCocktailBinding>()

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
    }

    private fun observeSave() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is AddCocktailState.Initial -> {}

                is AddCocktailState.ValidationError -> {
                    binding.nameEt.error =
                        if (state.isNameBlank) getString(R.string.add_cocktail_name) else null

                    if (state.areIngredientsEmpty) {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.add_ingredients), Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                is AddCocktailState.SaveError -> {

                }

                is AddCocktailState.SaveSuccessful -> {
                    goBack()
                }

                else -> {}
            }
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

    private fun showIngredientDialog() {
        val dialogFragment = IngredientDialogFragment()
        initIngredientDialogResultListener()
        dialogFragment.show(parentFragmentManager, null)
    }

    private fun initIngredientDialogResultListener() {
        requireActivity().supportFragmentManager.setFragmentResultListener(
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