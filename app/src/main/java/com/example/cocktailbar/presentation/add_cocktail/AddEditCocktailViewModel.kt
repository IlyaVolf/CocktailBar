package com.example.cocktailbar.presentation.add_cocktail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktailbar.domain.CocktailsRepository
import com.example.cocktailbar.domain.entities.AddCocktailData
import com.example.cocktailbar.domain.entities.Cocktail
import com.example.cocktailbar.utils.share
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class AddEditCocktailViewModel @AssistedInject constructor(
    @Assisted private val cocktailId: Long,
    private val cocktailsRepository: CocktailsRepository
) : ViewModel() {

    val mode = if (cocktailId == DEFAULT_ID) {
        Mode.Add
    } else {
        Mode.Edit
    }
    private val _state = MutableLiveData<AddEditCocktailState>(AddEditCocktailState.Initial)
    val state = _state.share()

    init {
        load()
    }

    fun tryAgain() {
        load()
    }

    fun save(addCocktailData: AddCocktailData) {

        if (addCocktailData.name.isBlank() || addCocktailData.ingredients.isEmpty()) {
            _state.value = AddEditCocktailState.ValidationError(
                addCocktailData.name.isBlank(),
                addCocktailData.ingredients.isEmpty()
            )
            return
        }

        viewModelScope.launch {
            try {
                _state.postValue(AddEditCocktailState.InProcess)
                val cocktail = Cocktail(
                    id = if (cocktailId != DEFAULT_ID) cocktailId else 0,
                    name = addCocktailData.name,
                    description = addCocktailData.description,
                    ingredients = addCocktailData.ingredients,
                    recipe = addCocktailData.recipe,
                    image = addCocktailData.image
                )
                when (mode) {
                    Mode.Add -> {
                        cocktailsRepository.addCocktail(cocktail)
                    }

                    Mode.Edit -> {
                        cocktailsRepository.updateCocktail(cocktail)
                    }
                }
                _state.postValue(AddEditCocktailState.SaveSuccessful)
            } catch (e: Exception) {
                _state.postValue(AddEditCocktailState.Error(e))
            }
        }
    }

    private fun load() = viewModelScope.launch {
        if (mode == Mode.Add) {
            _state.postValue(AddEditCocktailState.DataReady(null))
            return@launch
        }

        try {
            _state.postValue(AddEditCocktailState.InProcess)
            cocktailsRepository.getById(cocktailId).collect { cocktail ->
                _state.postValue(AddEditCocktailState.DataReady(cocktail))
            }
        } catch (e: Exception) {
            _state.postValue(AddEditCocktailState.Error(e))
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(cocktailId: Long): AddEditCocktailViewModel
    }

    companion object {
        enum class Mode { Add, Edit }

        const val DEFAULT_ID = -1L
    }

}