package com.example.cocktailbar.domain

import com.example.cocktailbar.domain.entities.Cocktail
import kotlinx.coroutines.flow.Flow

interface CocktailsRepository {

    suspend fun getCocktails(): Flow<List<Cocktail>>

    suspend fun getById(id: Long): Flow<Cocktail>

    suspend fun addCocktail(cocktail: Cocktail)

    suspend fun updateCocktail(cocktail: Cocktail)

    suspend fun deleteCocktail(cocktail: Cocktail)

}