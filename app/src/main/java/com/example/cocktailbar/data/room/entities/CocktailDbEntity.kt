package com.example.cocktailbar.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cocktailbar.domain.entities.AddCocktailData
import com.example.cocktailbar.domain.entities.Cocktail

@Entity(
    tableName = "cocktails"
)
data class CocktailDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String,
    val description: String,
    val ingredients: List<String>,
    val recipe: String
) {

    fun toCocktail(): Cocktail = Cocktail(
        id = id,
        name = name,
        description = description,
        ingredients = ingredients,
        recipe = recipe
    )

    companion object {
        fun fromAddCocktailData(addCocktailData: AddCocktailData): CocktailDbEntity = CocktailDbEntity(
            id = 0,
            name = addCocktailData.name,
            description = addCocktailData.description,
            ingredients = addCocktailData.ingredients,
            recipe = addCocktailData.recipe
        )
    }

}