package com.mehmetpeker.recipe.presentation.main.screens.addRecipe

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.mehmetpeker.recipe.R
import com.mehmetpeker.recipe.RecipeApplication
import com.mehmetpeker.recipe.base.BaseViewModel
import com.mehmetpeker.recipe.data.entity.recipe.createRecipe.CreateRecipeRequest
import com.mehmetpeker.recipe.data.entity.recipe.createRecipe.Material
import com.mehmetpeker.recipe.data.entity.recipe.createRecipe.Measurement
import com.mehmetpeker.recipe.data.repository.recipe.RecipeRepositoryImpl
import com.mehmetpeker.recipe.presentation.main.screens.addRecipe.uiModel.CategoriesUiModel
import com.mehmetpeker.recipe.presentation.main.screens.addRecipe.uiModel.MaterialsUiModel
import com.mehmetpeker.recipe.util.ApiError
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
import kotlinx.coroutines.flow.onEach
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

    init {
        getAllCategories()
        getAllMaterials()
    }

    var buttonVisibility by mutableStateOf(false)
    var selectedFile by mutableStateOf<File?>(null)
    var uploadedImageUrl by mutableStateOf<String>("")
    var imageUploadStatus by mutableStateOf<ImageUploadStatus>(ImageUploadStatus.IDLE)
    val ingredients = mutableStateOf(listOf<MaterialsUiModel>())
    private val defaultIngredients = MaterialsUiModel(-1, "", 0, "")

    private var _categories = MutableStateFlow<List<CategoriesUiModel>>(emptyList())
    val categories = _categories.asStateFlow()

    private var _selectedCategory = MutableStateFlow<CategoriesUiModel?>(null)
    val selectedCategory = _selectedCategory.asStateFlow()

    private var _materials = MutableStateFlow<List<MaterialsUiModel>>(emptyList())
    val materials = _materials.asStateFlow()

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

    var createRecipeResult by mutableStateOf(false)
    val recipeNameValidationResult: StateFlow<ValidationResult> =
        recipeName
            .debounce(500)
            .mapLatest { TextFieldValidator.validateRecipeName(it.text) }.onEach {
                checkButtonEnabled()
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = ValidationResult()
            )
    val recipeDescriptionValidationResult: StateFlow<ValidationResult> =
        recipeDescription
            .debounce(500)
            .mapLatest { TextFieldValidator.validateRecipeDescription(it.text) }.onEach {
                checkButtonEnabled()
            }
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

            }.onEach {
                checkButtonEnabled()
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

            }.onEach {
                checkButtonEnabled()
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

            }.onEach {
                checkButtonEnabled()
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = ValidationResult()
            )

    private fun getAllCategories() = viewModelScope.launch {
        val response = withContext(recipeDispatcher.io) {
            recipeRepositoryImpl.getAllCategories()
        }
        if (response is ApiSuccess) {
            val categoriesUiModelList = response.data.map {
                CategoriesUiModel(
                    id = it.id,
                    name = it.name
                )
            }
            _categories.emit(categoriesUiModelList)
        }
    }

    private fun getAllMaterials() = viewModelScope.launch {
        val response = withContext(recipeDispatcher.io) {
            recipeRepositoryImpl.getAllMaterials()
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

    fun updateSelectedCategory(categoriesUiModel: CategoriesUiModel) = viewModelScope.launch {
        _selectedCategory.emit(categoriesUiModel)
        checkButtonEnabled()
    }

    fun updateRecipeDescription(textFieldValue: TextFieldValue) = viewModelScope.launch {
        _recipeDescription.emit(textFieldValue)
        checkButtonEnabled()
    }

    fun updateRecipeName(textFieldValue: TextFieldValue) = viewModelScope.launch {
        _recipeName.emit(textFieldValue)
        checkButtonEnabled()
    }

    fun updateServes(textFieldValue: TextFieldValue) = viewModelScope.launch {
        _servesAmount.emit(textFieldValue)
        checkButtonEnabled()
    }

    fun updateCookTime(textFieldValue: TextFieldValue) = viewModelScope.launch {
        _cookTime.emit(textFieldValue)
        checkButtonEnabled()
    }

    fun updatePreparationTime(textFieldValue: TextFieldValue) = viewModelScope.launch {
        _preparationTime.emit(textFieldValue)
        checkButtonEnabled()
    }

    fun addIngredient() {
        ingredients.value = ingredients.value.toMutableList().also { it.add(defaultIngredients) }
        checkButtonEnabled()
    }

    fun updateIngredient(index: Int, newIngredients: MaterialsUiModel) {
        ingredients.value = ingredients.value.toMutableList().also {
            it[index] = newIngredients
        }
        checkButtonEnabled()
    }

    fun onRemove(removeIndex: Int) {
        ingredients.value = ingredients.value.toMutableList().also {
            it.removeAt(removeIndex)
        }
        checkButtonEnabled()
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
        checkButtonEnabled()
    }

    private fun checkButtonEnabled() = viewModelScope.launch {
        buttonVisibility =
            imageUploadStatus == ImageUploadStatus.SUCCESS && ingredients.value.isNotEmpty()
                    && recipeNameValidationResult.value.isSuccess
                    && recipeDescriptionValidationResult.value.isSuccess
                    && recipeServeValidationResult.value.isSuccess
                    && recipeCookingTimeValidationResult.value.isSuccess
                    && recipePreparationTimeValidationResult.value.isSuccess
                    && selectedCategory.value != null
    }

    fun createRecipe() = viewModelScope.launch {
        if (!buttonVisibility) return@launch
        val request = CreateRecipeRequest(
            categoryId = selectedCategory.value?.id ?: -1,
            cookingTime = cookTime.value.text.toIntOrNull(),
            description = recipeDescription.value.text,
            materials = ingredients.value.map {
                Material(
                    id = it.id,
                    name = it.name,
                    measurement = Measurement(
                        amount = it.amount.toDouble(),
                        unit = it.measurement
                    )
                )
            },
            name = recipeName.value.text,
            photoUrl = uploadedImageUrl,
            photoUrls = listOf(uploadedImageUrl),
            portions = servesAmount.value.text.toIntOrNull(),
            preparitionTime = preparationTime.value.text.toIntOrNull()
        )
        showProgress()
        val response = withContext(recipeDispatcher.io) {
            recipeRepositoryImpl.createRecipe(request)
        }
        when (response) {
            is ApiSuccess -> {
                createRecipeResult = true
            }

            is ApiError -> {
                _error.value = response
            }
        }
    }
}