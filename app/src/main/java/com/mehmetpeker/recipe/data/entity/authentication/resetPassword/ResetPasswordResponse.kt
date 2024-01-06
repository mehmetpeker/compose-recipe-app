package com.mehmetpeker.recipe.data.entity.authentication.resetPassword

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResetPasswordResponse(@SerialName("isSucces") val isSuccess: Boolean)
