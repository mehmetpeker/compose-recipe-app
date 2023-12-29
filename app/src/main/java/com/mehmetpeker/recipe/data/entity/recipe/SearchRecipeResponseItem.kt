package com.mehmetpeker.recipe.data.entity.recipe


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchRecipeResponseItem(
    @SerialName("categoryId")
    val categoryId: Int?,
    @SerialName("cookingTime")
    val cookingTime: Int?,
    @SerialName("description")
    val description: String?,
    @SerialName("id")
    val id: Int?,
    @SerialName("materials")
    val materials: Materials?,
    @SerialName("name")
    val name: String?,
    @SerialName("photoUrl")
    val photoUrl: String?,
    @SerialName("portions")
    val portions: Int?,
    @SerialName("preparitionTime")
    val preparitionTime: Int?,
    @SerialName("userId")
    val userId: Int?
)