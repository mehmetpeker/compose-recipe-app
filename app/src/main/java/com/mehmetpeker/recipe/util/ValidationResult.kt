package com.mehmetpeker.recipe.util

data class ValidationResult(
    val isSuccess: Boolean = false,
    val errorMessage: List<String>? = null
)
