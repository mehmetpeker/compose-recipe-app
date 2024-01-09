package com.mehmetpeker.recipe.presentation.main.screens.recipeDetail

import androidx.lifecycle.viewModelScope
import com.mehmetpeker.recipe.base.BaseViewModel
import com.mehmetpeker.recipe.data.entity.recipe.getAllRecipe.GetAllRecipeResponseItem
import com.mehmetpeker.recipe.data.repository.recipe.RecipeRepositoryImpl
import com.mehmetpeker.recipe.util.ApiError
import com.mehmetpeker.recipe.util.ApiSuccess
import com.mehmetpeker.recipe.util.RecipeDispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecipeDetailViewModel(
    private val recipeDispatcher: RecipeDispatchers,
    private val recipeRepositoryImpl: RecipeRepositoryImpl
) : BaseViewModel() {

    data class RecipeDetailSuccessUiState(
        val recipeDetail: GetAllRecipeResponseItem
    )

    sealed class RecipeDetailUiState {
        data object LOADING : RecipeDetailUiState()
        data object FAILED : RecipeDetailUiState()
        data class Success(val uiState: RecipeDetailSuccessUiState) : RecipeDetailUiState()
    }

    private val _recipeDetailUiState =
        MutableStateFlow<RecipeDetailUiState>(RecipeDetailUiState.LOADING)
    val recipeDetailUiState = _recipeDetailUiState.asStateFlow()

    fun getRecipeDetail(recipeId: String) = viewModelScope.launch {
        val response = withContext(recipeDispatcher.io) {
            recipeRepositoryImpl.getRecipe(recipeId = recipeId)
        }
        when (response) {
            is ApiSuccess -> {
                _recipeDetailUiState.value =
                    RecipeDetailUiState.Success(RecipeDetailSuccessUiState(recipeDetail = response.data))
            }

            is ApiError -> {
                _recipeDetailUiState.value =
                    RecipeDetailUiState.FAILED
            }
        }

    }
}