package com.mehmetpeker.recipe.presentation.home

import androidx.lifecycle.viewModelScope
import com.mehmetpeker.recipe.base.BaseViewModel
import com.mehmetpeker.recipe.util.SessionManager
import com.mehmetpeker.recipe.util.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

data class HomeUiState(
    val user: User? = null
)

class HomeViewModel : BaseViewModel(), KoinComponent {
    private val sessionManager: SessionManager by inject()
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()


    init {
        getUserData()
    }

    private fun getUserData() = viewModelScope.launch {
        _uiState.update {
            val newUser = sessionManager.retrieveUserData()
            it.copy(user = newUser)
        }
    }
}