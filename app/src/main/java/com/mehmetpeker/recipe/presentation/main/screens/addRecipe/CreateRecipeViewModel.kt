package com.mehmetpeker.recipe.presentation.main.screens.addRecipe

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.mehmetpeker.recipe.base.BaseViewModel
import com.mehmetpeker.recipe.util.ValidationResult
import com.mehmetpeker.recipe.util.validator.TextFieldValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CreateRecipeViewModel : BaseViewModel() {
    private var _recipeName = MutableStateFlow(TextFieldValue(""))
    val recipeName = _recipeName.asStateFlow()

    private var _recipeDescription = MutableStateFlow(TextFieldValue(""))
    val recipeDescription = _recipeDescription.asStateFlow()

    val recipeNameValidationResult: StateFlow<ValidationResult> =
        recipeName
            .debounce(500)
            .mapLatest { TextFieldValidator.validateRecipeName(it.text) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = ValidationResult()
            )
    val recipeDescriptionValidationResult: StateFlow<ValidationResult> =
        recipeDescription
            .debounce(500)
            .mapLatest { TextFieldValidator.validateRecipeDescription(it.text) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = ValidationResult()
            )


    fun updateRecipeDescription(textFieldValue: TextFieldValue) = viewModelScope.launch {
        _recipeDescription.emit(textFieldValue)
    }

    fun updateRecipeName(textFieldValue: TextFieldValue) = viewModelScope.launch {
        _recipeName.emit(textFieldValue)
    }
}