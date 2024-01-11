package com.mehmetpeker.recipe.data.entity.recipe.recipeComments


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecipeCommentsResponseItem(
    @SerialName("content")
    val content: String?,
    @SerialName("createdAt")
    val createdAt: String?,
    @SerialName("id")
    val id: Int?,
    @SerialName("lastUpdatedAt")
    val lastUpdatedAt: String?,
    @SerialName("recipeId")
    val recipeId: Int?,
    @SerialName("user")
    val user: User?,
    @SerialName("userId")
    val userId: Int?
)