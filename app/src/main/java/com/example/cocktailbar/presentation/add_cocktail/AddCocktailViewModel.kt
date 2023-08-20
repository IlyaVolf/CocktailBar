package com.example.cocktailbar.presentation.add_cocktail

import androidx.lifecycle.MutableLiveData
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
import kotlinx.coroutines.launch

class AddCocktailViewModel @AssistedInject constructor(
    private val cocktailsRepository: CocktailsRepository
) : ViewModel() {

    private val _state = MutableLiveData<AddCocktailState>(AddCocktailState.Initial)
    val state = _state.share()
    fun save(addCocktailData: AddCocktailData) {

        if (addCocktailData.name.isBlank() || addCocktailData.ingredients.isEmpty()) {
            _state.value = AddCocktailState.ValidationError(
                addCocktailData.name.isBlank(),
                addCocktailData.ingredients.isEmpty()
            )
            return
        }

        viewModelScope.launch {
            try {
                _state.postValue(AddCocktailState.Saving)
                val cocktail = Cocktail(
                    id = 0,
                    name = addCocktailData.name,
                    description = addCocktailData.description,
                    ingredients = addCocktailData.ingredients,
                    recipe = addCocktailData.recipe,
                    image = addCocktailData.image
                )
                cocktailsRepository.addCocktail(cocktail)
                _state.postValue(AddCocktailState.SaveSuccessful)
            } catch (e: Exception) {
                _state.postValue(AddCocktailState.SaveError(e))
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(): AddCocktailViewModel
    }

}