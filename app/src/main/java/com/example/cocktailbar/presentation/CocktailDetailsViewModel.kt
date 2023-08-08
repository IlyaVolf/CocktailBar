package com.example.cocktailbar.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktailbar.domain.CocktailsRepository
import com.example.cocktailbar.domain.entities.Cocktail
import com.example.cocktailbar.utils.DataHolder
import com.example.cocktailbar.utils.ObservableHolder
import com.example.cocktailbar.utils.share
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class CocktailDetailsViewModel @AssistedInject constructor(
    @Assisted private val cocktailId: Long,
    private val cocktailsRepository: CocktailsRepository
) : ViewModel() {

    private val _cocktail = ObservableHolder<Cocktail>(DataHolder.init())
    val cocktail = _cocktail.share()

    init {
        Log.d("ABCDEF", "CocktailDetailsViewModel")
        _cocktail.postValue(DataHolder.loading())
        load()
    }

    fun tryAgain() {
        _cocktail.postValue(DataHolder.loading())
        load()
    }

    private fun load() = viewModelScope.launch {
        try {
            val cocktail = cocktailsRepository.getById(cocktailId)
            _cocktail.postValue(DataHolder.ready(cocktail))
        } catch (e: Exception) {
            _cocktail.value = DataHolder.error(e)
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(cocktailId: Long): CocktailDetailsViewModel
    }

}