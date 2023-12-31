package com.example.cocktailbar.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import com.example.cocktailbar.data.room.entities.CocktailDbEntity

@Dao
interface CocktailsDao {

    @Query("SELECT * FROM cocktails WHERE :id = id")
    fun getById(id: Long): Flow<CocktailDbEntity>

    @Query("SELECT * FROM cocktails")
    fun getCocktails(): Flow<List<CocktailDbEntity>>

    @Insert
    suspend fun addCocktail(cocktailDbEntity: CocktailDbEntity)

    @Update
    suspend fun updateCocktail(cocktailDbEntity: CocktailDbEntity)

    @Delete
    suspend fun deleteCocktail(cocktailDbEntity: CocktailDbEntity)

    @Query("DELETE FROM cocktails")
    suspend fun deleteAllCocktails()

}