package com.example.cocktailbar.data

import com.example.cocktailbar.data.room.CocktailsDao
import com.example.cocktailbar.data.room.mappers.CocktailMapper
import com.example.cocktailbar.di.IoDispatcher
import com.example.cocktailbar.domain.CocktailsRepository
import com.example.cocktailbar.domain.entities.AddCocktailData
import com.example.cocktailbar.domain.entities.Cocktail
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoomCocktailsRepository @Inject constructor(
    private val cocktailsDao: CocktailsDao,
    private val cocktailMapper: CocktailMapper,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : CocktailsRepository {

    override suspend fun getCocktails(): List<Cocktail> = withContext(ioDispatcher) {
        val cocktailDbEntityList = cocktailsDao.getCocktails()
        return@withContext cocktailDbEntityList.map { cocktailMapper.toCocktail(it) }
    }

    override suspend fun getById(id: Long): Cocktail = withContext(ioDispatcher) {
        val cocktailDbEntity = cocktailsDao.getById(id)
        return@withContext cocktailMapper.toCocktail(cocktailDbEntity)
    }

    override suspend fun addCocktail(cocktail: Cocktail) = withContext(ioDispatcher) {
        val cocktailDbEntity = cocktailMapper.toCocktailDbEntity(cocktail)
        cocktailsDao.addCocktail(cocktailDbEntity)
    }

    override suspend fun updateCocktail(cocktail: Cocktail) = withContext(ioDispatcher) {
        val cocktailDbEntity = cocktailMapper.toCocktailDbEntity(cocktail)
        cocktailsDao.updateCocktail(cocktailDbEntity)
    }

    override suspend fun deleteCocktail(cocktail: Cocktail) = withContext(ioDispatcher) {
        val cocktailDbEntity = cocktailMapper.toCocktailDbEntity(cocktail)
        cocktailsDao.deleteCocktail(cocktailDbEntity)
    }
}