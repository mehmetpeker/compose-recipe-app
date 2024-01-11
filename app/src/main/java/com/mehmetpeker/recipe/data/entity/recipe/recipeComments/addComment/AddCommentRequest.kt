package com.mehmetpeker.recipe.data.entity.recipe.recipeComments.addComment

import kotlinx.serialization.Serializable

@Serializable
data class AddCommentRequest(
    val recipeId: String,
    val content: String
)
