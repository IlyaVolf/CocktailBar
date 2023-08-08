package com.example.cocktailbar.data.room.mappers

import com.example.cocktailbar.data.room.entities.CocktailDbEntity
import com.example.cocktailbar.domain.entities.Cocktail
import com.example.cocktailbar.utils.IngredientsToStringConvertor
import javax.inject.Singleton

@Singleton
class CocktailMapper {

    fun toCocktail(cocktailDbEntity: CocktailDbEntity): Cocktail = Cocktail(
        id = cocktailDbEntity.id,
        name = cocktailDbEntity.name,
        description = cocktailDbEntity.description,
        ingredients = IngredientsToStringConvertor.toList(cocktailDbEntity.ingredients),
        recipe = cocktailDbEntity.recipe
    )

    fun toCocktailDbEntity(cocktail: Cocktail): CocktailDbEntity = CocktailDbEntity(
        id = cocktail.id,
        name = cocktail.name,
        description = cocktail.description,
        ingredients = IngredientsToStringConvertor.toString(cocktail.ingredients),
        recipe = cocktail.recipe
    )

}