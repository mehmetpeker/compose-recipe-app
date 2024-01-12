package com.mehmetpeker.recipe.data.entity.recipe.searchRecipeIncludedMaterials


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchRecipeWithIncludedMaterialsRequestItem(
    @SerialName("id")
    val id: Int?,
    @SerialName("name")
    val name: String?
)