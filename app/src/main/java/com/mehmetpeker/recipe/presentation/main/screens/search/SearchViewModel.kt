package com.mehmetpeker.recipe.presentation.main.screens.search

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.mehmetpeker.recipe.base.BaseViewModel
import com.mehmetpeker.recipe.data.entity.recipe.SearchRecipeResponseItem
import com.mehmetpeker.recipe.data.repository.recipe.RecipeRepositoryImpl
import com.mehmetpeker.recipe.presentation.main.screens.addRecipe.uiModel.MaterialsUiModel
import com.mehmetpeker.recipe.util.ApiError
import com.mehmetpeker.recipe.util.ApiSuccess
import com.mehmetpeker.recipe.util.RecipeDispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent

class SearchViewModel(
    private val recipeDispatcher: RecipeDispatchers,
    private val recipeRepository: RecipeRepositoryImpl
) : BaseViewModel(), KoinComponent {

    init {
        getAllMaterials()
    }

    private val _searchUiState = MutableStateFlow<SearchUiState>(SearchUiState.Idle)
    val searchUiState = _searchUiState

    val searchTextFieldValue = mutableStateOf(TextFieldValue(""))
    private var _materials = MutableStateFlow<List<MaterialsUiModel>>(emptyList())
    val materials = _materials.asStateFlow()

    private var _selectedMaterials = MutableStateFlow<List<MaterialsUiModel>>(emptyList())
    val selectedMaterials = _selectedMaterials.asStateFlow()


    sealed class SearchUiState {
        data class RecipeFound(val list: List<SearchRecipeResponseItem>) : SearchUiState()
        data object NotFound : SearchUiState()
        data object Idle : SearchUiState()
    }

    fun searchRecipe(searchText: String) = viewModelScope.launch {
        if (searchText.isBlank()) return@launch
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

    fun addMaterialToFilter(material: MaterialsUiModel) = viewModelScope.launch {
        _selectedMaterials.value = _selectedMaterials.value.toMutableList().apply { add(material) }
    }

    fun removeMaterialToFilter(material: MaterialsUiModel) = viewModelScope.launch {
        _selectedMaterials.value =
            _selectedMaterials.value.toMutableList().apply { remove(material) }
    }

    private fun getAllMaterials() = viewModelScope.launch {
        val response = withContext(recipeDispatcher.io) {
            recipeRepository.getAllMaterials()
        }
        if (response is ApiSuccess) {
            val materialsUiModelList = response.data.map {
                MaterialsUiModel(
                    id = it.id,
                    name = it.name,
                    measurement = it.measurement
                )
            }
            _materials.emit(materialsUiModelList)
        }
    }

    fun changeTextFieldValue(newValue: TextFieldValue) = viewModelScope.launch {
        searchTextFieldValue.value = newValue
    }
}