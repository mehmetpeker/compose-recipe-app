package com.mehmetpeker.recipe.util

data class ValidationResult(
    val isSuccess: Boolean,
    val errorMessage: List<String>? = null
)
