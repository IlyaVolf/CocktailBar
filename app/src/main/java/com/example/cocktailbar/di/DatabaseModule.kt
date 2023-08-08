package com.example.cocktailbar.di

import android.content.Context
import androidx.room.Room
import com.example.cocktailbar.data.room.AppDatabase
import com.example.cocktailbar.data.room.CocktailsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "database.db")
   //         .createFromAsset("initial_database.db")
            .build()
    }

    @Provides
    @Singleton
    fun provideCocktailsDao(database: AppDatabase): CocktailsDao {
        return database.getCocktailsDao()
    }

}