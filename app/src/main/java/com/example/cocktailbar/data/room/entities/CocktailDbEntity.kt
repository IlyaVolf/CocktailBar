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
    val ingredients: String,
    val recipe: String,
    val image: String?,
)