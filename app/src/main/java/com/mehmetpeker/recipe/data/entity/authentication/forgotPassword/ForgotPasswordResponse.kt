package com.mehmetpeker.recipe.data.entity.authentication.forgotPassword

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForgotPasswordResponse(@SerialName("isSucces") val isSuccess: Boolean)
