package com.mehmetpeker.recipe.data.entity.authentication.resetPassword


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResetPasswordRequest(
    @SerialName("confirmPassword")
    val confirmPassword: String?,
    @SerialName("email")
    val email: String?,
    @SerialName("password")
    val password: String?,
    @SerialName("token")
    val token: String?
)