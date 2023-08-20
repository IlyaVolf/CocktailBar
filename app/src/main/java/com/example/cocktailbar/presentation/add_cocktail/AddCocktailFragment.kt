package com.example.cocktailbar.presentation.add_cocktail

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.cocktailbar.R
import com.example.cocktailbar.databinding.FragmentAddCocktailBinding
import com.example.cocktailbar.domain.entities.AddCocktailData
import com.example.cocktailbar.utils.DataHolder
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
        observeSave()
    }

    private fun initListeners() {
        initSaveButtonListener()
        initCancelButtonListener()
    }

    private fun observeSave() {
        viewModel.save.observe(viewLifecycleOwner) { holder ->
            when (holder) {
                is DataHolder.READY -> {
                    goBack()
                }

                else -> {}
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

    // TODO implement getIngredients()
    private fun getIngredients(): List<String> {
        return listOf(
            "9 cups sprite",
            "small bunch mint",
            "3 limes, juiced"
        )
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
            negativeButtonText= negativeButtonText,
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
            positiveButtonAction= null,
        )
    }

}