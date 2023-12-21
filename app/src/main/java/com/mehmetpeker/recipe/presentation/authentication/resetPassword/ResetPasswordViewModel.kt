package com.mehmetpeker.recipe.presentation.authentication.resetPassword

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.mehmetpeker.recipe.base.BaseViewModel
import com.mehmetpeker.recipe.util.ValidationResult
import com.mehmetpeker.recipe.util.validator.TextFieldValidator
import kotlinx.coroutines.launch

class ResetPasswordViewModel : BaseViewModel() {

    var emailField by mutableStateOf(TextFieldValue(""))
    var emailValidationResult: ValidationResult? by mutableStateOf(null)
    fun onEmailTextChanged(value: TextFieldValue) = viewModelScope.launch {
        emailField = value
    }

    fun validateEmail(onValidate: () -> Unit) = viewModelScope.launch {
        emailValidationResult = TextFieldValidator.validateEmail(emailField.text)
        if (emailValidationResult != null && emailValidationResult!!.isSuccess) {
            onValidate()
        }
    }

    fun resetPassword() = viewModelScope.launch {

    }
}