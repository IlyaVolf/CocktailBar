package com.example.cocktailbar.presentation.my_cocktails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktailbar.domain.CocktailsRepository
import com.example.cocktailbar.utils.share
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class MyCocktailsViewModel @AssistedInject constructor(
    private val cocktailsRepository: CocktailsRepository
) : ViewModel() {

    private val _state = MutableLiveData<MyCocktailsState>(MyCocktailsState.Initial)
    val state = _state.share()

    init {
         load()
    }

    fun refresh() {
        load()
    }

    fun tryAgain() {
        load()
    }

    private fun load() = viewModelScope.launch {
        try {
            _state.postValue(MyCocktailsState.Loading)
            cocktailsRepository.getCocktails().collect {
                _state.postValue(MyCocktailsState.Ready(it))
            }
        } catch (e: Exception) {
            _state.postValue(MyCocktailsState.Error)
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(): MyCocktailsViewModel
    }

}