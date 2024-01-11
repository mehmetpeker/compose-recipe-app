package com.mehmetpeker.recipe.presentation.main.screens.recipeDetail

import androidx.lifecycle.viewModelScope
import com.mehmetpeker.recipe.base.BaseViewModel
import com.mehmetpeker.recipe.data.entity.recipe.getRecipe.GetRecipeResponse
import com.mehmetpeker.recipe.data.repository.recipe.RecipeRepositoryImpl
import com.mehmetpeker.recipe.util.ApiError
import com.mehmetpeker.recipe.util.ApiSuccess
import com.mehmetpeker.recipe.util.RecipeDispatchers
import com.mehmetpeker.recipe.util.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecipeDetailViewModel(
    private val recipeDispatcher: RecipeDispatchers,
    private val recipeRepositoryImpl: RecipeRepositoryImpl,
    private val sessionManager: SessionManager
) : BaseViewModel() {

    data class RecipeDetailSuccessUiState(
        val recipeDetail: GetRecipeResponse
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

    fun likeRecipe(recipeId: String, isAlreadyLiked: Boolean) = viewModelScope.launch {
        //TODO login olduktan sonra servisin userId bilgisi sağlaması gerekli
        val response = withContext(recipeDispatcher.io) {
            if (isAlreadyLiked) recipeRepositoryImpl.dislikeRecipe(
                recipeId,
                "1"
            ) else recipeRepositoryImpl.likeRecipe(recipeId)
        }
        when (response) {
            is ApiSuccess -> {
                val successUiState =
                    _recipeDetailUiState.value as? RecipeDetailUiState.Success ?: return@launch
                val recipeData = successUiState.uiState.recipeDetail.recipe
                val newSuccessUiState = successUiState.uiState.recipeDetail.copy(
                    recipe = recipeData?.copy(
                        isCurrentUserLikeRecipe = recipeData.isCurrentUserLikeRecipe?.not(),
                        likesCount = if (isAlreadyLiked) recipeData.likesCount?.minus(1) else recipeData.likesCount?.plus(
                            1
                        )
                    )
                )
                _recipeDetailUiState.value =
                    RecipeDetailUiState.Success(RecipeDetailSuccessUiState(recipeDetail = newSuccessUiState))
            }

            else -> Unit
        }

    }
}