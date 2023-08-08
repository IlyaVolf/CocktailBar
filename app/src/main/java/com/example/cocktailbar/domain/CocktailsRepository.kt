package com.example.cocktailbar.domain

import com.example.cocktailbar.domain.entities.Cocktail

interface CocktailsRepository {

    suspend fun getCocktails(): List<Cocktail>

    suspend fun getById(id: Long): Cocktail

    suspend fun addCocktail(cocktail: Cocktail)

    suspend fun updateCocktail(cocktail: Cocktail)

    suspend fun deleteCocktail(cocktail: Cocktail)

}