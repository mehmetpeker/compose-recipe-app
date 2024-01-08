package com.mehmetpeker.recipe.presentation.main.screens.addRecipe

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.mehmetpeker.recipe.R
import com.mehmetpeker.recipe.RecipeApplication
import com.mehmetpeker.recipe.base.BaseViewModel
import com.mehmetpeker.recipe.data.model.Ingredients
import com.mehmetpeker.recipe.data.repository.recipe.RecipeRepositoryImpl
import com.mehmetpeker.recipe.util.ApiSuccess
import com.mehmetpeker.recipe.util.RecipeDispatchers
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
import kotlinx.coroutines.withContext
import java.io.File

class CreateRecipeViewModel(
    private val recipeDispatcher: RecipeDispatchers,
    private val recipeRepositoryImpl: RecipeRepositoryImpl
) : BaseViewModel() {
    enum class ImageUploadStatus {
        IDLE,
        LOADING,
        SUCCESS,
        FAILED
    }

    var selectedFile by mutableStateOf<File?>(null)
    var uploadedImageUrl by mutableStateOf<String>("")
    var imageUploadStatus by mutableStateOf<ImageUploadStatus>(ImageUploadStatus.IDLE)
    val ingredients = mutableStateOf(listOf<Ingredients>())
    private val defaultIngredients = Ingredients("", 0, "")

    private var _recipeName = MutableStateFlow(TextFieldValue(""))
    val recipeName = _recipeName.asStateFlow()

    private var _recipeDescription = MutableStateFlow(TextFieldValue(""))
    val recipeDescription = _recipeDescription.asStateFlow()

    private var _servesAmount = MutableStateFlow(TextFieldValue(""))
    val servesAmount = _servesAmount.asStateFlow()

    private var _cookTime = MutableStateFlow(TextFieldValue(""))
    val cookTime = _cookTime.asStateFlow()

    private var _preparationTime = MutableStateFlow(TextFieldValue(""))
    val preparationTime = _preparationTime.asStateFlow()

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

    val recipeServeValidationResult: StateFlow<ValidationResult> =
        servesAmount
            .debounce(500)
            .mapLatest {
                val condition = it.text.toIntOrNull() != null
                val errorMessage =
                    listOf(RecipeApplication.getAppContext().getString(R.string.invalid_input))
                ValidationResult(
                    isSuccess = condition,
                    errorMessage = if (condition) emptyList() else errorMessage
                )

            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = ValidationResult()
            )
    val recipeCookingTimeValidationResult: StateFlow<ValidationResult> =
        cookTime
            .debounce(500)
            .mapLatest {
                val condition = it.text.toIntOrNull() != null
                val errorMessage =
                    listOf(RecipeApplication.getAppContext().getString(R.string.invalid_input))
                ValidationResult(
                    isSuccess = condition,
                    errorMessage = if (condition) emptyList() else errorMessage
                )

            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = ValidationResult()
            )

    val recipePreparationTimeValidationResult: StateFlow<ValidationResult> =
        preparationTime
            .debounce(500)
            .mapLatest {
                val condition = it.text.toIntOrNull() != null
                val errorMessage =
                    listOf(RecipeApplication.getAppContext().getString(R.string.invalid_input))
                ValidationResult(
                    isSuccess = condition,
                    errorMessage = if (condition) emptyList() else errorMessage
                )

            }
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

    fun updateServes(textFieldValue: TextFieldValue) = viewModelScope.launch {
        _servesAmount.emit(textFieldValue)
    }

    fun updateCookTime(textFieldValue: TextFieldValue) = viewModelScope.launch {
        _cookTime.emit(textFieldValue)
    }

    fun updatePreparationTime(textFieldValue: TextFieldValue) = viewModelScope.launch {
        _preparationTime.emit(textFieldValue)
    }

    fun addIngredient() {
        ingredients.value = ingredients.value.toMutableList().also { it.add(defaultIngredients) }
    }

    fun updateIngredient(index: Int, newIngredients: Ingredients) {
        ingredients.value = ingredients.value.toMutableList().also {
            it[index] = newIngredients
        }
    }

    fun onRemove(removeIndex: Int) {
        ingredients.value = ingredients.value.toMutableList().also {
            it.removeAt(removeIndex)
        }
    }

    fun onImageSelected(file: File?) {
        selectedFile = file
        onImageUpload()
    }

    fun onImageUpload() = viewModelScope.launch {
        if (selectedFile == null) return@launch
        imageUploadStatus = ImageUploadStatus.LOADING
        val response = withContext(recipeDispatcher.io) {
            recipeRepositoryImpl.uploadRecipePhoto(selectedFile!!)
        }
        when (response) {
            is ApiSuccess -> {
                uploadedImageUrl = response.data.url
                imageUploadStatus = ImageUploadStatus.SUCCESS
            }

            else -> imageUploadStatus = ImageUploadStatus.FAILED
        }
    }
}