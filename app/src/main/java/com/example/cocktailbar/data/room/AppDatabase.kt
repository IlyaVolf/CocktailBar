package com.example.cocktailbar.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cocktailbar.data.room.entities.CocktailDbEntity
import com.example.cocktailbar.data.room.entities.IngredientDbEntity

@Database(
    version = 1,
    entities = [
        CocktailDbEntity::class,
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getCocktailsDao(): CocktailsDao

}