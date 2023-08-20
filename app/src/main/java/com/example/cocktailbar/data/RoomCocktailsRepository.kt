package com.example.cocktailbar.data

import com.example.cocktailbar.data.room.CocktailsDao
import com.example.cocktailbar.data.room.entities.CocktailDbEntity
import com.example.cocktailbar.data.room.mappers.CocktailMapper
import com.example.cocktailbar.di.IoDispatcher
import com.example.cocktailbar.domain.CocktailsRepository
import com.example.cocktailbar.domain.entities.AddCocktailData
import com.example.cocktailbar.domain.entities.Cocktail
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoomCocktailsRepository @Inject constructor(
    private val cocktailsDao: CocktailsDao,
    private val cocktailMapper: CocktailMapper,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : CocktailsRepository {

    override suspend fun getCocktails(): Flow<List<Cocktail>> {
        val cocktailDbEntityList = cocktailsDao.getCocktails()

        delay(1000)

        return cocktailDbEntityList.map { list -> list.map { cocktailMapper.toCocktail(it) } }
    }

    override suspend fun getById(id: Long): Flow<Cocktail> {
        val cocktailDbEntity = cocktailsDao.getById(id)

        delay(1000)

        return cocktailDbEntity.map { cocktailMapper.toCocktail(it) }
    }

    override suspend fun addCocktail(cocktail: Cocktail) = withContext(ioDispatcher) {
        val cocktailDbEntity = cocktailMapper.toCocktailDbEntity(cocktail)

        delay(1000)


        cocktailsDao.addCocktail(cocktailDbEntity)
    }

    override suspend fun updateCocktail(cocktail: Cocktail) = withContext(ioDispatcher) {
        val cocktailDbEntity = cocktailMapper.toCocktailDbEntity(cocktail)

        delay(1000)

        cocktailsDao.updateCocktail(cocktailDbEntity)
    }

    override suspend fun deleteCocktail(cocktail: Cocktail) = withContext(ioDispatcher) {
        val cocktailDbEntity = cocktailMapper.toCocktailDbEntity(cocktail)

        delay(1000)

        cocktailsDao.deleteCocktail(cocktailDbEntity)
    }
}