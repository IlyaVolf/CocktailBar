package com.example.cocktailbar.presentation.add_cocktail

import com.example.cocktailbar.domain.entities.Cocktail

sealed class AddEditCocktailState {

    object Initial : AddEditCocktailState()

    object InProcess : AddEditCocktailState()

    data class DataReady(val cocktail: Cocktail?) : AddEditCocktailState()

    data class ValidationError(val isNameBlank: Boolean, val areIngredientsEmpty: Boolean) :
        AddEditCocktailState()

    object SaveSuccessful : AddEditCocktailState()

    data class Error(val error: Throwable) : AddEditCocktailState()

}