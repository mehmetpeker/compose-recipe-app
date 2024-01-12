package com.mehmetpeker.recipe.data.entity.user.uploadProfilePhoto

import kotlinx.serialization.Serializable

@Serializable
data class UploadProfilePhotoResponse(
    val id: Int?,
    val url: String?,
    val isMain: Boolean?
)
