package com.mehmetpeker.recipe.domain.repository

interface UserRepository {
    suspend fun setAccessToken(token: String)
    suspend fun getAccessToken(): String?

    suspend fun setUsername(username: String)
    suspend fun setEmail(email: String)
    suspend fun setProfilePhotoUrl(url: String)
    suspend fun setRememberLogin(remember: Boolean)
    suspend fun logOut()
}