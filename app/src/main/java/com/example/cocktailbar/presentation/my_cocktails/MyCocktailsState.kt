package com.example.cocktailbar.presentation.my_cocktails

import com.example.cocktailbar.domain.entities.Cocktail

sealed class MyCocktailsState {

    object Initial : MyCocktailsState()
    object Loading : MyCocktailsState()

    data class Ready(val cocktailsList: List<Cocktail>) : MyCocktailsState()

    object Error : MyCocktailsState()

}