package com.mehmetpeker.recipe.data.entity.recipe.getRecipe


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LikedRecipe(
    @SerialName("photoUrl")
    val photoUrl: String?,
    @SerialName("recipeId")
    val recipeId: Int?,
    @SerialName("recipeTitle")
    val recipeTitle: String?,
    @SerialName("userId")
    val userId: Int?
)