package com.mehmetpeker.recipe.data.entity.authentication.forgotPassword

import kotlinx.serialization.Serializable

@Serializable
data class ForgotPasswordRequest(val email: String)
