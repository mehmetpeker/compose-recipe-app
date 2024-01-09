package com.mehmetpeker.recipe.data.entity.recipe.categories


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetAllCategoriesItem(
    @SerialName("id")
    val id: Int?,
    @SerialName("name")
    val name: String?
)