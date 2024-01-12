package com.mehmetpeker.recipe.data.entity.recipe.getRecipe


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Photo(
    @SerialName("id")
    val id: Int?,
    @SerialName("isMain")
    val isMain: Boolean?,
    @SerialName("url")
    val url: String?
)