package com.example.cocktailbar.domain.entities

data class AddCocktailData(
    val name: String,
    val description: String,
    val ingredients: List<String>,
    val recipe: String,
    val image: String
)

