package com.example.cocktailbar.presentation.add_cocktail

import com.example.cocktailbar.domain.entities.Cocktail
import com.example.cocktailbar.presentation.cocktail_details.CocktailDetailsState

sealed class AddCocktailState {

    object Initial : AddCocktailState()

    data class ValidationError(val isNameBlank: Boolean, val areIngredientsEmpty: Boolean) : AddCocktailState()

    object Saving : AddCocktailState()

    data class SaveError(val error: Throwable) : AddCocktailState()

    object SaveSuccessful : AddCocktailState()

}