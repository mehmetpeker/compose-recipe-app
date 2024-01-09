package com.mehmetpeker.recipe.data.entity.recipe.createRecipe

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateRecipeResponse(
    @SerialName("isSucces")
    val isSuccess: Boolean
)
