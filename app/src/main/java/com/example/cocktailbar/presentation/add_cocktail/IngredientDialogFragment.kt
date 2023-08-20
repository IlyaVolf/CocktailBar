package com.example.cocktailbar.presentation.add_cocktail

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.example.cocktailbar.R
import com.example.cocktailbar.databinding.AddIngredientDialogBinding

class IngredientDialogFragment : DialogFragment() {

    private var _binding: AddIngredientDialogBinding? = null
    private val binding: AddIngredientDialogBinding
        get() = _binding ?: throw RuntimeException("AddIngredientDialogBinding is null")

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            _binding = AddIngredientDialogBinding.inflate(LayoutInflater.from(context))

            AlertDialog.Builder(it)
                .setView(binding.root)
                .create()
        } ?: throw IllegalStateException("Activity cannot be null")

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareButtons()
    }

    private fun prepareButtons() {
        with(binding) {
            buttonAdd.setOnClickListener {
                val ingredient = editTextIngredient.text.toString()
                if (ingredient.isBlank()) {
                    TextFieldIngredient.error = getString(R.string.fill_ingredient_field)
                } else {
                    parentFragmentManager.setFragmentResult(
                        INGREDIENT_REQUEST_CODE, bundleOf(INGREDIENT to ingredient)
                    )
                    dismiss()
                }
            }
            buttonCloseDialog.setOnClickListener {
                dismiss()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object Result {
        const val INGREDIENT_REQUEST_CODE = "INGREDIENT_REQUEST_CODE"
        const val INGREDIENT = "INGREDIENT"
    }
}