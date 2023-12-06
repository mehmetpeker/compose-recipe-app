package com.mehmetpeker.recipe.presentation.authentication.login

import androidx.lifecycle.viewModelScope
import com.mehmetpeker.recipe.base.BaseViewModel
import com.mehmetpeker.recipe.data.entity.authentication.login.LoginRequest
import com.mehmetpeker.recipe.data.entity.authentication.login.LoginResponse
import com.mehmetpeker.recipe.data.network.authentication.AuthenticationServiceImpl
import com.mehmetpeker.recipe.util.ApiError
import com.mehmetpeker.recipe.util.ApiSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(private val service: AuthenticationServiceImpl) : BaseViewModel() {

    private val _loginResponse = MutableStateFlow<LoginResponse?>(null)
    val loginResponse = _loginResponse
    suspend fun login() = viewModelScope.launch {
        val response = withContext(Dispatchers.IO) {
            service.login(LoginRequest("", ""))
        }
        when (response) {
            is ApiSuccess -> _loginResponse.emit(response.data)
            is ApiError -> _error.emit(response)
        }

    }
}