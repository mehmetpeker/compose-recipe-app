package com.mehmetpeker.recipe.data.entity.recipe.createRecipe


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Material(
    @SerialName("id")
    val id: Int?,
    @SerialName("measurement")
    val measurement: Measurement?,
    @SerialName("name")
    val name: String?
)