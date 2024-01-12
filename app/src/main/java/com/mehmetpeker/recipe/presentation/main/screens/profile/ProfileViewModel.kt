package com.mehmetpeker.recipe.presentation.main.screens.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.mehmetpeker.recipe.base.BaseViewModel
import com.mehmetpeker.recipe.data.UserRepositoryImpl
import com.mehmetpeker.recipe.data.entity.recipe.getRecipe.Recipe
import com.mehmetpeker.recipe.util.ApiError
import com.mehmetpeker.recipe.util.ApiSuccess
import com.mehmetpeker.recipe.util.RecipeDispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

val emptyProfileUiState = ProfileViewModel.UiState()

class ProfileViewModel(
    private val recipeDispatchers: RecipeDispatchers,
    private val userRepositoryImpl: UserRepositoryImpl
) : BaseViewModel() {
    enum class ImageUploadStatus {
        IDLE,
        LOADING,
        SUCCESS,
        FAILED
    }

    data class UiState(
        val profilePhotoUrl: String = "",
        val userEmail: String = "",
        val userName: String = "",
        val userRecipes: List<Recipe?> = emptyList(),
        val likedRecipes: List<Recipe?> = emptyList()
    )

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    var selectedFile by mutableStateOf<File?>(null)
    var uploadedImageUrl by mutableStateOf<String>("")
    var imageUploadStatus by mutableStateOf<ImageUploadStatus>(
        ImageUploadStatus.IDLE
    )

    fun getProfileInformation() = viewModelScope.launch {
        val response = withContext(recipeDispatchers.io) {
            userRepositoryImpl.getProfileInformation()
        }
        when (response) {
            is ApiSuccess -> {
                _uiState.update {
                    it.copy(
                        profilePhotoUrl = response.data.profilePhoto ?: "",
                        userEmail = response.data.email ?: "",
                        userName = response.data.username ?: "",
                        userRecipes = response.data.recipes ?: emptyList(),
                        likedRecipes = response.data.likedRecipes ?: emptyList()
                    )
                }
            }

            is ApiError -> {
                _error.value = response
            }
        }

    }

    fun onImageSelected(file: File?) {
        selectedFile = file
        onImageUpload()
    }

    fun onImageUpload() = viewModelScope.launch {
        if (selectedFile == null) return@launch
        imageUploadStatus = ImageUploadStatus.LOADING
        val response = withContext(recipeDispatchers.io) {
            userRepositoryImpl.uploadProfilePhoto(selectedFile!!)
        }
        when (response) {
            is ApiSuccess -> {
                uploadedImageUrl = response.data.url ?: ""
                imageUploadStatus = ImageUploadStatus.SUCCESS
            }

            else -> imageUploadStatus = ImageUploadStatus.FAILED
        }
    }
}