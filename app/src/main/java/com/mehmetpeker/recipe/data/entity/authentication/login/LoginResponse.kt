package com.mehmetpeker.recipe.data.entity.authentication.login


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    @SerialName("photoUrl")
    val photoUrl: String?,
    @SerialName("token")
    val token: String?,
    @SerialName("username")
    val username: String?
)