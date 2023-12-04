package com.mehmetpeker.recipe.data.entity.authentication.login


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    @SerialName("password")
    val password: String?,
    @SerialName("username")
    val username: String?
)