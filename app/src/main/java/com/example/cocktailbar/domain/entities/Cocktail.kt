package com.example.cocktailbar.domain.entities

data class Cocktail(
    val id: Long,
    val name: String,
    val description: String,
    val ingredients: List<String>,
    val recipe: String
)

