package com.mehmetpeker.recipe.presentation.onboarding

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.mehmetpeker.recipe.base.BaseViewModel
import com.mehmetpeker.recipe.util.SessionManager
import com.mehmetpeker.recipe.util.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class OnBoardingViewModel : BaseViewModel(), KoinComponent {
    private val sessionManager: SessionManager by inject()
    var user: User? by mutableStateOf(null)

    init {
        retrieveUser()
    }

    private fun retrieveUser() = viewModelScope.launch {
        delay(3000)
        user = sessionManager.retrieveUserData()
    }
}