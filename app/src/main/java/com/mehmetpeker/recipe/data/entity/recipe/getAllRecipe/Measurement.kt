package com.mehmetpeker.recipe.data.entity.recipe.getAllRecipe


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Measurement(
    @SerialName("amount")
    val amount: Double?,
    @SerialName("unit")
    val unit: String?
)