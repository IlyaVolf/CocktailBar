package com.example.cocktailbar.presentation.cocktail_details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktailbar.domain.CocktailsRepository
import com.example.cocktailbar.domain.entities.Cocktail
import com.example.cocktailbar.utils.share
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class CocktailDetailsViewModel @AssistedInject constructor(
    @Assisted private val cocktailId: Long,
    private val cocktailsRepository: CocktailsRepository
) : ViewModel() {

    private val _state = MutableLiveData<CocktailDetailsState>(CocktailDetailsState.Initial)
    val state = _state.share()

    init {
        load()
    }

    fun tryAgain() {
        load()
    }

    fun deleteCocktail(cocktail: Cocktail?) = viewModelScope.launch {
        if (cocktail == null) {
            return@launch
        }

        try {
            _state.postValue(CocktailDetailsState.Loading)
            cocktailsRepository.deleteCocktail(cocktail)
            _state.postValue(CocktailDetailsState.DeletionReady)
        } catch (e: Exception) {
            _state.postValue(CocktailDetailsState.Error(e))
        }
    }

    private fun load() = viewModelScope.launch {
        try {
            _state.postValue(CocktailDetailsState.Loading)
            cocktailsRepository.getById(cocktailId).collect { cocktail ->
                _state.postValue(CocktailDetailsState.DataReady(cocktail))
            }
        } catch (e: Exception) {
            _state.postValue(CocktailDetailsState.Error(e))
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(cocktailId: Long): CocktailDetailsViewModel
    }

}