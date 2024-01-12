package com.mehmetpeker.recipe.data.entity.recipe.getRecipe


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Recipe(
    @SerialName("categoryId")
    val categoryId: Int?,
    @SerialName("cookingTime")
    val cookingTime: Int?,
    @SerialName("description")
    val description: String?,
    @SerialName("id")
    val id: Int?,
    @SerialName("isCurrentUserLikeRecipe")
    val isCurrentUserLikeRecipe: Boolean?,
    @SerialName("likesCount")
    val likesCount: Int?,
    @SerialName("materials")
    val materials: List<Material?>?,
    @SerialName("name")
    val name: String?,
    @SerialName("photoUrl")
    val photoUrl: String?,
    @SerialName("photoUrls")
    val photoUrls: List<Photo?>?,
    @SerialName("portions")
    val portions: Int?,
    @SerialName("preparitionTime")
    val preparitionTime: Int?,
    @SerialName("userId")
    val userId: Int?
)