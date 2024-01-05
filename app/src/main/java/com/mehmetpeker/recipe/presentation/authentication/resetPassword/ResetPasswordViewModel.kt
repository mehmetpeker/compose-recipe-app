package com.mehmetpeker.recipe.presentation.authentication.resetPassword

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.mehmetpeker.recipe.base.BaseViewModel
import com.mehmetpeker.recipe.data.entity.authentication.resetPassword.ResetPasswordRequest
import com.mehmetpeker.recipe.data.repository.authentication.AuthenticationRepositoryImpl
import com.mehmetpeker.recipe.util.ApiError
import com.mehmetpeker.recipe.util.ApiSuccess
import com.mehmetpeker.recipe.util.RecipeDispatchers
import com.mehmetpeker.recipe.util.ValidationResult
import com.mehmetpeker.recipe.util.validator.TextFieldValidator
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ResetPasswordViewModel(
    private val recipeDispatcher: RecipeDispatchers,
    private val authenticationRepositoryImpl: AuthenticationRepositoryImpl
) : BaseViewModel() {

    sealed class ResetPasswordUiState {
        data object Initial : ResetPasswordUiState()
        data object Success : ResetPasswordUiState()
    }

    var email by mutableStateOf("")
    var token by mutableStateOf("")
    var password by mutableStateOf(TextFieldValue(""))
    var passwordConfirm by mutableStateOf(TextFieldValue(""))
    var resetPasswordValidation: ValidationResult? by mutableStateOf(null)

    var uiState by mutableStateOf<ResetPasswordUiState>(ResetPasswordUiState.Initial)
    fun onPasswordTextChanged(value: TextFieldValue) = viewModelScope.launch {
        password = value
    }

    fun onPasswordConfirmTextChanged(value: TextFieldValue) = viewModelScope.launch {
        passwordConfirm = value
    }

    fun validateForm(onValidate: () -> Unit) = viewModelScope.launch {
        resetPasswordValidation =
            TextFieldValidator.validateResetPassword(password.text, password.text)
        if (resetPasswordValidation != null && resetPasswordValidation!!.isSuccess) {
            onValidate()
        }
    }

    fun resetPassword(
    ) = viewModelScope.launch {
        showProgress()
        val response = withContext(recipeDispatcher.io) {
            authenticationRepositoryImpl.resetPassword(
                ResetPasswordRequest(
                    password = password.text,
                    confirmPassword = password.text,
                    email = email,
                    token = token
                )
            )
        }
        when (response) {
            is ApiSuccess -> {
                uiState = ResetPasswordUiState.Success
            }

            is ApiError -> {
                _error.value = response
            }
        }
        hideProgress()
    }
}