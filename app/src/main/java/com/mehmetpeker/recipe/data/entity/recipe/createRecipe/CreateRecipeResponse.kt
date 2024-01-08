package com.mehmetpeker.recipe.data.entity.recipe.createRecipe

import kotlinx.serialization.Serializable

@Serializable
data class CreateRecipeResponse(
    val isSuccess: Boolean
)
