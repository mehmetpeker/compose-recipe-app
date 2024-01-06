package com.mehmetpeker.recipe.data.entity.authentication.updatePassword

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdatePasswordResponse(@SerialName("isSucces") val isSuccess: Boolean)
