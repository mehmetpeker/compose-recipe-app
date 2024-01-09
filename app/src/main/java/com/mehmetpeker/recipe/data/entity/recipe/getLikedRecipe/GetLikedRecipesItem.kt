package com.mehmetpeker.recipe.data.entity.recipe.getLikedRecipe


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetLikedRecipesItem(
    @SerialName("photoUrl")
    val photoUrl: String?,
    @SerialName("recipeId")
    val recipeId: Int?,
    @SerialName("recipeTitle")
    val recipeTitle: String?,
    @SerialName("userId")
    val userId: Int?
)