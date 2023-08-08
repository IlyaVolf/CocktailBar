package com.example.cocktailbar.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import com.example.cocktailbar.data.room.entities.IngredientDbEntity

@Dao
interface IngredientsDao {

    @Query("SELECT * FROM ingredient WHERE :id = id")
    suspend fun getById(id: Long): IngredientDbEntity

    /*@Query("SELECT * FROM cocktails WHERE :id = id")
    fun getById(id: Long): Flow<IngredientDbEntity?>*/

    @Query("SELECT * FROM ingredient")
    suspend fun getIngredients(): List<IngredientDbEntity>

    @Insert
    suspend fun addIngredient(cocktailDbEntity: IngredientDbEntity)

    @Update
    suspend fun updateIngredient(cocktailDbEntity: IngredientDbEntity)

    @Delete
    suspend fun deleteIngredient(cocktailDbEntity: IngredientDbEntity)

}