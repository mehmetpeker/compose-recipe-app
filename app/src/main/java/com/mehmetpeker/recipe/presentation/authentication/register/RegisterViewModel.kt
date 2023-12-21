package com.mehmetpeker.recipe.presentation.authentication.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.mehmetpeker.recipe.base.BaseViewModel
import com.mehmetpeker.recipe.data.entity.authentication.register.RegisterRequest
import com.mehmetpeker.recipe.data.repository.authentication.AuthenticationRepositoryImpl
import com.mehmetpeker.recipe.util.ApiError
import com.mehmetpeker.recipe.util.ApiSuccess
import com.mehmetpeker.recipe.util.ValidationResult
import com.mehmetpeker.recipe.util.validator.TextFieldValidator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterViewModel(private val authenticationRepository: AuthenticationRepositoryImpl) :
    BaseViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()
    var email by mutableStateOf(TextFieldValue(""))
    var username by mutableStateOf(TextFieldValue(""))
    var password by mutableStateOf(TextFieldValue(""))

    data class UiState(
        val emailResult: ValidationResult? = null,
        val usernameResult: ValidationResult? = null,
        val passwordResult: ValidationResult? = null,
        val isValid: Boolean = false,
        val registerState: Boolean = false
    )

    fun changeRegisterState(state: Boolean) = viewModelScope.launch {
        _uiState.update {
            it.copy(registerState = state)
        }
    }

    fun validateInputs() {
        viewModelScope.launch {
            val emailResult = TextFieldValidator.validateEmail(email.text)
            val usernameResult = TextFieldValidator.validateUsername(username.text)
            val passwordResult = TextFieldValidator.validatePassword(password.text)

            val isValid =
                emailResult.isSuccess && usernameResult.isSuccess && passwordResult.isSuccess
            _uiState.update {
                it.copy(
                    isValid = isValid,
                    emailResult = emailResult,
                    usernameResult = usernameResult,
                    passwordResult = passwordResult
                )
            }
        }
    }

    fun register() = viewModelScope.launch {
        val requestModel = RegisterRequest(
            username.text, email.text, password.text
        )
        showProgress()
        val response = withContext(Dispatchers.IO) {
            authenticationRepository.register(requestModel)
        }
        when (response) {
            is ApiSuccess -> {
                _uiState.update {
                    it.copy(registerState = true)
                }
            }

            is ApiError -> {
                _error.value = response
                resetUiState()
            }
        }
        hideProgress()
    }

    private fun resetUiState() = viewModelScope.launch {
        _uiState.value = UiState()
    }
}