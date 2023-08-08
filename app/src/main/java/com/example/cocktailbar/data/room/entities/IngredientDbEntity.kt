package com.example.cocktailbar.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "ingredient"
)
data class IngredientDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val cocktailId: Long,
    val description: String
)