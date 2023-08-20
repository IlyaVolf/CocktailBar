package com.example.cocktailbar.presentation.my_cocktails

import com.example.cocktailbar.domain.entities.Cocktail
import com.example.cocktailbar.presentation.cocktail_details.CocktailDetailsState

sealed class MyCocktailsState {

    object Initial : MyCocktailsState()
    object Loading : MyCocktailsState()

    data class DataReady(val cocktailsList: List<Cocktail>) : MyCocktailsState()

    data class Error(val error: Throwable) : MyCocktailsState()

}