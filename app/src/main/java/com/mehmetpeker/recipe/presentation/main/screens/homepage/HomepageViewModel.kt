package com.mehmetpeker.recipe.presentation.main.screens.homepage

import androidx.lifecycle.viewModelScope
import com.mehmetpeker.recipe.R
import com.mehmetpeker.recipe.RecipeApplication
import com.mehmetpeker.recipe.base.BaseViewModel
import com.mehmetpeker.recipe.data.repository.recipe.RecipeRepositoryImpl
import com.mehmetpeker.recipe.domain.uimodel.recipe.RecipeItemUiModel
import com.mehmetpeker.recipe.domain.uimodel.recipe.toRecipeItemUiModel
import com.mehmetpeker.recipe.presentation.main.screens.addRecipe.uiModel.CategoriesUiModel
import com.mehmetpeker.recipe.util.ApiSuccess
import com.mehmetpeker.recipe.util.RecipeDispatchers
import com.mehmetpeker.recipe.util.SessionManager
import com.mehmetpeker.recipe.util.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

data class HomeUiState(
    val user: User? = null,
    val categories: List<CategoriesUiModel> = emptyList(),
    val recipes: List<RecipeItemUiModel>? = null
)

class HomepageViewModel(
    private val recipeDispatcher: RecipeDispatchers,
    private val recipeRepositoryImpl: RecipeRepositoryImpl,
) : BaseViewModel(), KoinComponent {
    private val sessionManager: SessionManager by inject()
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()


    init {
        getAllCategories()
        getAllRecipe()
    }

    fun getUserData() = viewModelScope.launch {
        _uiState.update {
            val newUser = sessionManager.retrieveUserData()
            it.copy(user = newUser)
        }
    }

    private fun getAllCategories() = viewModelScope.launch {
        val allCategoriesItem = CategoriesUiModel(
            id = Int.MAX_VALUE,
            name = RecipeApplication.getAppContext().getString(R.string.all)
        )
        showProgress()
        val response = withContext(recipeDispatcher.io) {
            recipeRepositoryImpl.getAllCategories()
        }
        if (response is ApiSuccess) {
            val categoriesUiModelList = response.data.map {
                CategoriesUiModel(
                    id = it.id,
                    name = it.name
                )
            }.toMutableList()
            categoriesUiModelList.add(0, allCategoriesItem)
            _uiState.update {
                it.copy(categories = categoriesUiModelList)
            }
        }
        hideProgress()
    }

    fun getAllRecipe() = viewModelScope.launch {
        showProgress()
        val response = withContext(recipeDispatcher.io) {
            recipeRepositoryImpl.getAllRecipes()
        }
        if (response is ApiSuccess) {
            val recipesUiModelList = response.data.map { it.toRecipeItemUiModel() }
            _uiState.update {
                it.copy(recipes = recipesUiModelList)
            }
        }
        hideProgress()
    }

    fun getAllRecipeByCategory(categoryId: String) = viewModelScope.launch {
        showProgress()
        val response = withContext(recipeDispatcher.io) {
            recipeRepositoryImpl.getAllRecipesByCategory(categoryId)
        }
        if (response is ApiSuccess) {
            val recipesUiModelList = response.data.map { it.toRecipeItemUiModel() }
            _uiState.update {
                it.copy(recipes = recipesUiModelList)
            }
        }
        hideProgress()
    }
}