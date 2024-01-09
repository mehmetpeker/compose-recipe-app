package com.mehmetpeker.recipe.presentation.main.screens.favorites

import androidx.lifecycle.viewModelScope
import com.mehmetpeker.recipe.base.BaseViewModel
import com.mehmetpeker.recipe.data.repository.recipe.RecipeRepositoryImpl
import com.mehmetpeker.recipe.domain.uimodel.recipe.RecipeItemUiModel
import com.mehmetpeker.recipe.domain.uimodel.recipe.toRecipeItemUiModel
import com.mehmetpeker.recipe.util.ApiError
import com.mehmetpeker.recipe.util.ApiSuccess
import com.mehmetpeker.recipe.util.RecipeDispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoritesViewModel(
    private val recipeDispatchers: RecipeDispatchers,
    private val recipeRepositoryImpl: RecipeRepositoryImpl
) : BaseViewModel() {
    private val _likedRecipes = MutableStateFlow<List<RecipeItemUiModel>?>(null)
    val likedRecipes = _likedRecipes
    fun getFavoritesRecipe() = viewModelScope.launch {
        showProgress()
        val response = withContext(recipeDispatchers.io) {
            recipeRepositoryImpl.getLikedRecipes()
        }
        when (response) {
            is ApiSuccess -> {
                _likedRecipes.value = response.data.map {
                    RecipeItemUiModel(
                        recipeId = it.recipeId.toString(),
                        recipeName = it.recipeTitle ?: "-",
                        recipePhotoUrl = it.photoUrl ?: ""
                    )
                }
            }

            is ApiError -> {
                _error.value = response
            }
        }
        hideProgress()
    }
}