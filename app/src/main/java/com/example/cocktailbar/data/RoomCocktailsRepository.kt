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

    // TODO remove this temporary method which inits some cocktails info
    private suspend fun initDb(size: Int): Flow<List<CocktailDbEntity>> {
        cocktailsDao.deleteAllCocktails()
        if (size < 0) {
            addCocktail(
                Cocktail(
                    id = 0,
                    name = "Milkshake",
                    description = "Tasty shaken milk is loved by everyone",
                    ingredients = listOf("Milk", "Strawberry"),
                    recipe = "Step 1\n" +
                            "In a blender, blend together ice cream and milk. \n" +
                            "Step 2\n" +
                            "Pour into a glass and garnish with whipped topping, sprinkles, and a cherry.",
                    image = "android.resource://com.example.cocktailbar/drawable/milkshake"
                ),
            )
            addCocktail(
                Cocktail(
                    id = 0,
                    name = "Dad's heaven",
                    description = "Cocktail for real dads with a secret formula",
                    ingredients = listOf(""),
                    recipe = "It is a secret",
                    image = "android.resource://com.example.cocktailbar/drawable/banana_heaven"
                )
            )
            addCocktail(
                Cocktail(
                    id = 0,
                    name = "Water",
                    description = "",
                    ingredients = listOf("Water"),
                    recipe = "",
                    image = null
                )
            )
        }
        return cocktailsDao.getCocktails()
    }

    override suspend fun getCocktails(): Flow<List<Cocktail>> {
        var cocktailDbEntityList = cocktailsDao.getCocktails()

        delay(1000)

        // TODO remove later
        //cocktailDbEntityList = initDb(cocktailDbEntityList.size)

        return cocktailDbEntityList.map { list -> list.map { cocktailMapper.toCocktail(it) } }
    }

    override suspend fun getById(id: Long): Cocktail = withContext(ioDispatcher) {
        val cocktailDbEntity = cocktailsDao.getById(id)

        delay(1000)

        return@withContext cocktailMapper.toCocktail(cocktailDbEntity)
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