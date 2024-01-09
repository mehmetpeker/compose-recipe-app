package com.mehmetpeker.recipe.presentation.main.screens.addRecipe.uiModel

data class CategoriesUiModel(
    val id: Int?,
    val name: String?
)

val INVALID_CATEGORY = CategoriesUiModel(
    null, null
)
