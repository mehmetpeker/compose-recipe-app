package com.mehmetpeker.recipe.data.entity.recipe.createRecipe


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateRecipeRequest(
    @SerialName("categoryId")
    val categoryId: Int?,
    @SerialName("cookingTime")
    val cookingTime: Int?,
    @SerialName("description")
    val description: String?,
    @SerialName("materials")
    val materials: List<Material?>?,
    @SerialName("name")
    val name: String?,
    @SerialName("photoUrl")
    val photoUrl: String?,
    @SerialName("portions")
    val portions: Int?,
    @SerialName("preparitionTime")
    val preparitionTime: Int?
)