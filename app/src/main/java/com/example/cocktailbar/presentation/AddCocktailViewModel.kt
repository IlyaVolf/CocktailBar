package com.example.cocktailbar.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktailbar.domain.CocktailsRepository
import com.example.cocktailbar.domain.entities.AddCocktailData
import com.example.cocktailbar.domain.entities.Cocktail
import com.example.cocktailbar.utils.DataHolder
import com.example.cocktailbar.utils.ObservableHolder
import com.example.cocktailbar.utils.share
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddCocktailViewModel @AssistedInject constructor(
    private val cocktailsRepository: CocktailsRepository
) : ViewModel() {

    private val _save = ObservableHolder<Unit>(DataHolder.init())
    val save = _save.share()
    fun save(addCocktailData: AddCocktailData) = viewModelScope.launch {
        try {
            _save.value = DataHolder.loading()
            val cocktail = Cocktail(
                id = 0,
                name = addCocktailData.name,
                description = addCocktailData.description,
                ingredients = addCocktailData.ingredients,
                recipe = addCocktailData.recipe,
                image = addCocktailData.image
            )
            cocktailsRepository.addCocktail(cocktail)
            _save.postValue(DataHolder.ready(Unit))
        } catch (e: Exception) {
            _save.value = DataHolder.error(e)
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(): AddCocktailViewModel
    }

}