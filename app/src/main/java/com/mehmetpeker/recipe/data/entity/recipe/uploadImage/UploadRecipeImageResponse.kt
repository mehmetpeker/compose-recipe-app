package com.mehmetpeker.recipe.data.entity.recipe.uploadImage

import kotlinx.serialization.Serializable

@Serializable
data class UploadRecipeImageResponse(
    val url: String
)
