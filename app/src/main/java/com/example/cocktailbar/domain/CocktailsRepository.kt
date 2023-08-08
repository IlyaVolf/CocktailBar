package com.example.cocktailbar.domain

import com.example.cocktailbar.domain.entities.Cocktail

interface CocktailsRepository {

    fun getCocktails(): List<Cocktail>

    fun getCocktail(id: String): Cocktail

    fun addCocktail(cocktail: Cocktail)

    fun updateCocktail(cocktail: Cocktail)

    fun deleteCocktail(cocktail: Cocktail)

}