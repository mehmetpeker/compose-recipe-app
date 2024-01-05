package com.mehmetpeker.recipe.presentation.authentication.forgotPassword

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.mehmetpeker.recipe.base.BaseViewModel
import com.mehmetpeker.recipe.data.entity.authentication.forgotPassword.ForgotPasswordRequest
import com.mehmetpeker.recipe.data.repository.authentication.AuthenticationRepositoryImpl
import com.mehmetpeker.recipe.util.ApiError
import com.mehmetpeker.recipe.util.ApiSuccess
import com.mehmetpeker.recipe.util.RecipeDispatchers
import com.mehmetpeker.recipe.util.ValidationResult
import com.mehmetpeker.recipe.util.validator.TextFieldValidator
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ForgotPasswordViewModel(
    private val dispatcher: RecipeDispatchers,
    private val authenticationRepositoryImpl: AuthenticationRepositoryImpl
) : BaseViewModel() {

    sealed class ForgotPasswordUiState {
        data object INITIAL : ForgotPasswordUiState()
        data object SUCCESS : ForgotPasswordUiState()
    }

    var emailField by mutableStateOf(TextFieldValue(""))
    var uiState by mutableStateOf<ForgotPasswordUiState>(ForgotPasswordUiState.INITIAL)
    var emailValidationResult: ValidationResult? by mutableStateOf(null)
    fun onEmailTextChanged(value: TextFieldValue) = viewModelScope.launch {
        emailField = value
    }

    fun validateEmail(onValidate: (mail: String) -> Unit) = viewModelScope.launch {
        emailValidationResult = TextFieldValidator.validateEmail(emailField.text)
        if (emailValidationResult != null && emailValidationResult!!.isSuccess) {
            onValidate(emailField.text)
        }
    }

    fun sendForgotPasswordEmail(email: String) = viewModelScope.launch {
        showProgress()
        val response = withContext(dispatcher.io) {
            authenticationRepositoryImpl.forgotPassword(ForgotPasswordRequest(email = email))
        }
        when (response) {
            is ApiSuccess -> {
                uiState = ForgotPasswordUiState.SUCCESS
            }

            is ApiError -> {
                _error.value = response
            }
        }
        hideProgress()
    }
}