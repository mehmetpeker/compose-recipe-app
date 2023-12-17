package com.mehmetpeker.recipe.presentation.authentication.register

import androidx.lifecycle.viewModelScope
import com.mehmetpeker.recipe.base.BaseViewModel
import com.mehmetpeker.recipe.util.ValidationResult
import com.mehmetpeker.recipe.util.validator.TextFieldValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel : BaseViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Invalid())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    sealed class UiState {
        data class Invalid(
            val emailResult: ValidationResult? = null,
            val usernameResult: ValidationResult? = null,
            val passwordResult: ValidationResult? = null
        ) : UiState()

        data object Valid : UiState()
    }

    fun validateInputs(username: String, email: String, password: String) {
        viewModelScope.launch {
            val emailResult = TextFieldValidator.validateEmail(email)
            val usernameResult = TextFieldValidator.validateUsername(username)
            val passwordResult = TextFieldValidator.validatePassword(password)

            val isValid =
                emailResult.isSuccess && usernameResult.isSuccess && passwordResult.isSuccess
            _uiState.value = if (isValid) UiState.Valid else UiState.Invalid(
                emailResult,
                usernameResult,
                passwordResult
            )
        }
    }

    fun register() = viewModelScope.launch {

    }
}