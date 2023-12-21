package com.mehmetpeker.recipe.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponseBody(
    @SerialName("errorCode") val errorCode: String?,
    @SerialName("errorMessage") val errorMessage: String?
)