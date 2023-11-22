package com.mehmetpeker.recipe.network

sealed interface NetworkResult<out T> {
    data object Loading : NetworkResult<Nothing>
    data class Success<out T>(val data: T) : NetworkResult<T>
    data class Error(val error: String? = null) : NetworkResult<Nothing>
}