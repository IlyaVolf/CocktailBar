package com.example.cocktailbar.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktailbar.domain.CocktailsRepository
import com.example.cocktailbar.domain.entities.Cocktail
import com.example.cocktailbar.utils.DataHolder
import com.example.cocktailbar.utils.ObservableHolder
import com.example.cocktailbar.utils.share
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class MyCocktailsViewModel @AssistedInject constructor(
    private val cocktailsRepository: CocktailsRepository
) : ViewModel() {

    private val _cocktailsList = ObservableHolder<List<Cocktail>>(DataHolder.init())
    val cocktailsList = _cocktailsList.share()

    init {
        _cocktailsList.postValue(DataHolder.loading())
        load()
    }

    fun tryAgain() {
        _cocktailsList.postValue(DataHolder.loading())
        load()
    }

    private fun load() = viewModelScope.launch {
        try {
            cocktailsRepository.addCocktail(Cocktail(0, "a", "b", listOf(), "", null))
            val cocktails = cocktailsRepository.getCocktails()
            _cocktailsList.postValue(DataHolder.ready(cocktails))
        } catch (e: Exception) {
            _cocktailsList.value = DataHolder.error(e)
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(): MyCocktailsViewModel
    }

}