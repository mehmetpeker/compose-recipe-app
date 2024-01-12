package com.mehmetpeker.recipe.data.entity.user.deletePhotoResponse

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DeleteProfilePhotoResponse(
    @SerialName("isSucces")
    val isSuccess: Boolean
)