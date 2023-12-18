package com.mehmetpeker.recipe.presentation.authentication.login

import androidx.lifecycle.viewModelScope
import com.mehmetpeker.recipe.base.BaseViewModel
import com.mehmetpeker.recipe.data.entity.authentication.login.LoginRequest
import com.mehmetpeker.recipe.data.entity.authentication.login.LoginResponse
import com.mehmetpeker.recipe.data.network.authentication.AuthenticationServiceImpl
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
import kotlin.random.Random

class LoginViewModel(private val service: AuthenticationServiceImpl) : BaseViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()


    data class UiState(
        val stateId: Int? = null,
        val loginResponse: LoginResponse? = null,
        val emailResult: ValidationResult? = null,
        val passwordResult: ValidationResult? = null,
        val isFormValid: Boolean = false
    )


    suspend fun login(email: String, password: String) = viewModelScope.launch {
        showProgress()
        val response = withContext(Dispatchers.IO) {
            service.login(LoginRequest(email, password))
        }
        when (response) {
            is ApiSuccess -> _uiState.update {
                it.copy(loginResponse = response.data)
            }

            is ApiError -> _error.emit(response)
        }
        hideProgress()
    }


    fun validateInputs(email: String, password: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    stateId = Random.nextInt(),
                    loginResponse = null,
                    emailResult = null,
                    passwordResult = null,
                    isFormValid = false,
                )
            }
            val emailResult = TextFieldValidator.validateEmail(email)
            val passwordResult = TextFieldValidator.validatePassword(password)
            val isValid =
                emailResult.isSuccess && passwordResult.isSuccess
            _uiState.update {
                it.copy(
                    stateId = Random.nextInt(),
                    emailResult = emailResult,
                    passwordResult = passwordResult,
                    isFormValid = isValid,
                )
            }
        }
    }
}