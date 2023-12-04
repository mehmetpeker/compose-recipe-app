package com.mehmetpeker.recipe.data.entity.authentication.updatePassword


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdatePasswordRequest(
    @SerialName("confirmNewPassword")
    val confirmNewPassword: String?,
    @SerialName("currentPassword")
    val currentPassword: String?,
    @SerialName("newPassword")
    val newPassword: String?,
    @SerialName("username")
    val username: String?
)