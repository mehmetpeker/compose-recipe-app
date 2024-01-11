package com.mehmetpeker.recipe.data.entity.recipe.recipeComments.addComment

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddCommentResponse(
    @SerialName("isSucces")
    val isSuccess: Boolean
)
