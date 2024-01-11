package com.mehmetpeker.recipe.data.entity.recipe.getRecipe


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetRecipeResponse(
    @SerialName("recipe")
    val recipe: Recipe?,
    @SerialName("user")
    val user: User?
)