package com.example.cocktailbar.presentation.cocktail_details

import com.example.cocktailbar.domain.entities.Cocktail

sealed class CocktailDetailsState {

    object Initial : CocktailDetailsState()
    object Loading : CocktailDetailsState()

    data class DataReady(val cocktail: Cocktail) : CocktailDetailsState()

    object DeletionReady : CocktailDetailsState()
    data class Error(val error: Throwable) : CocktailDetailsState()

}