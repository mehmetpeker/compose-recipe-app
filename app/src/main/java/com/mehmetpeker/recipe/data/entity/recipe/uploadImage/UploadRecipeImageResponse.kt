package com.mehmetpeker.recipe.data.entity.recipe.uploadImage

import kotlinx.serialization.Serializable

@Serializable
data class UploadRecipeImageResponse(
    val id: Int,
    val url: String,
    val isMain: Boolean
)
