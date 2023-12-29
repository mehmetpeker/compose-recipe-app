package com.mehmetpeker.recipe.presentation.main.screens.search

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.mehmetpeker.recipe.base.BaseViewModel
import com.mehmetpeker.recipe.data.entity.recipe.SearchRecipeResponse
import com.mehmetpeker.recipe.data.repository.recipe.RecipeRepositoryImpl
import com.mehmetpeker.recipe.util.ApiError
import com.mehmetpeker.recipe.util.ApiSuccess
import com.mehmetpeker.recipe.util.RecipeDispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent

class SearchViewModel(
    private val recipeDispatcher: RecipeDispatchers,
    private val recipeRepository: RecipeRepositoryImpl
) : BaseViewModel(), KoinComponent {
    private val _searchUiState = MutableStateFlow<SearchUiState>(SearchUiState.Idle)
    val searchUiState = _searchUiState

    val searchTextFieldValue = mutableStateOf(TextFieldValue(""))

    sealed class SearchUiState {
        data class RecipeFound(val list: SearchRecipeResponse) : SearchUiState()
        data object NotFound : SearchUiState()
        data object Idle : SearchUiState()
    }

    fun searchRecipe(searchText: String) = viewModelScope.launch {
        showProgress()
        val response = withContext(recipeDispatcher.io) {
            recipeRepository.searchRecipe(searchText)
        }
        when (response) {
            is ApiSuccess -> {
                val newState = if (response.data.isEmpty()) {
                    SearchUiState.NotFound
                } else {
                    SearchUiState.RecipeFound(list = response.data)
                }
                _searchUiState.value = newState
            }

            is ApiError -> {
                _error.value = response
            }
        }
        hideProgress()
    }

    fun changeTextFieldValue(newValue: TextFieldValue) = viewModelScope.launch {
        searchTextFieldValue.value = newValue
    }
}