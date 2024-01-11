package com.mehmetpeker.recipe.data.entity.recipe.likeRecipe

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LikeRecipeResponse(
    @SerialName("isSucces")
    val isSuccess: Boolean
)
