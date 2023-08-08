package com.example.cocktailbar.di

import com.example.cocktailbar.data.RoomCocktailsRepository
import com.example.cocktailbar.domain.CocktailsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesModule {

    @Binds
    abstract fun bindCocktailsRepository(
        charactersRepository: RoomCocktailsRepository,
    ): CocktailsRepository

}