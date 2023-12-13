package com.mehmetpeker.recipe.presentation.authentication.login

import androidx.lifecycle.viewModelScope
import com.mehmetpeker.recipe.base.BaseViewModel
import com.mehmetpeker.recipe.data.entity.authentication.login.LoginResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(/*private val service: AuthenticationServiceImpl*/) : BaseViewModel() {

    private val _loginResponse = MutableStateFlow<LoginResponse?>(null)
    val loginResponse = _loginResponse
    suspend fun login() = viewModelScope.launch {
        /*val response = withContext(Dispatchers.IO) {
            service.login(LoginRequest("", ""))
        }
        when (response) {
            is ApiSuccess -> _loginResponse.emit(response.data)
            is ApiError -> _error.emit(response)
        }
*/
    }
}