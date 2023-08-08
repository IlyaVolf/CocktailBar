package com.example.cocktailbar.data

import com.example.cocktailbar.domain.CocktailsRepository
import com.example.cocktailbar.domain.entities.Cocktail

class RoomCocktailsRepository : CocktailsRepository {

    override fun getCocktails(): List<Cocktail> {
        TODO("Not yet implemented")
    }

    override fun getCocktail(id: String): Cocktail {
        TODO("Not yet implemented")
    }

    override fun addCocktail(cocktail: Cocktail) {
        TODO("Not yet implemented")
    }

    override fun updateCocktail(cocktail: Cocktail) {
        TODO("Not yet implemented")
    }

    override fun deleteCocktail(cocktail: Cocktail) {
        TODO("Not yet implemented")
    }
}