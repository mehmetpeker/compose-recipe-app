package com.mehmetpeker.recipe.data

import com.mehmetpeker.recipe.data.local.preferences.RecipePreferences
import com.mehmetpeker.recipe.domain.repository.UserRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UserRepositoryImpl : UserRepository, KoinComponent {
    private val preferences: RecipePreferences by inject()
    override suspend fun setAccessToken(token: String) {
        preferences.accessToken = token
    }

    override suspend fun getAccessToken(): String? {
        return preferences.accessToken
    }

    override suspend fun setUsername(username: String) {
        preferences.userName = username
    }

    override suspend fun setEmail(email: String) {
        preferences.email = email
    }

    override suspend fun setProfilePhotoUrl(url: String) {
        preferences.profilePhotoUrl = url
    }

    override suspend fun setRememberLogin(remember: Boolean) {
        preferences.rememberLogin = remember
    }

    override suspend fun logOut() {
        with(preferences) {
            accessToken = ""
            userName = ""
            email = ""
            profilePhotoUrl = ""
            isUserLoggedIn = false
            rememberLogin = false
        }
    }
}