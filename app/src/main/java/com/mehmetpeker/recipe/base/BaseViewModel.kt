package com.mehmetpeker.recipe.base

import androidx.lifecycle.ViewModel
import com.mehmetpeker.recipe.util.ApiError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

open class BaseViewModel : ViewModel() {

    protected val _error: MutableStateFlow<ApiError<*>?> = MutableStateFlow(null)
    val error: StateFlow<ApiError<*>?> = _error


    private val _showProgress: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val showProgress: StateFlow<Boolean> = _showProgress

    fun showProgress() {
        _showProgress.value = true
    }

    fun hideProgress() {
        _showProgress.value = false
    }

    fun removeError() {
        _error.value = null
    }
}
