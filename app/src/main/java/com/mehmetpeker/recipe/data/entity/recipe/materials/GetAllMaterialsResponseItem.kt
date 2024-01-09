package com.mehmetpeker.recipe.data.entity.recipe.materials


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetAllMaterialsResponseItem(
    @SerialName("id")
    val id: Int?,
    @SerialName("measurement")
    val measurement: String?,
    @SerialName("name")
    val name: String?
)