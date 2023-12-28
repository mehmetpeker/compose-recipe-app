package com.mehmetpeker.recipe.domain.repository

import com.mehmetpeker.recipe.util.User

interface UserRepository {
    suspend fun setAccessToken(token: String)
    suspend fun getAccessToken(): String?
    suspend fun retrieveUserData(): User
    suspend fun setUsername(username: String)
    suspend fun getUsername(): String?
    suspend fun setEmail(email: String)
    suspend fun getEmail(): String?
    suspend fun setProfilePhotoUrl(url: String)
    suspend fun getProfilePhotoUrl(): String?
    suspend fun setRememberLogin(remember: Boolean)
    suspend fun getRememberLogin(): Boolean?
    suspend fun logOut()
}