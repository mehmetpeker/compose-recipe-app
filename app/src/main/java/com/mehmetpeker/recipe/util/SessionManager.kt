package com.mehmetpeker.recipe.util

import com.mehmetpeker.recipe.data.UserRepositoryImpl
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

data class User(
    val username: String?,
    val profilePhotoUrl: String?,
    val accessToken: String?,
    val isRemembered: Boolean?
)

class SessionManager : KoinComponent {
    private val userRepository: UserRepositoryImpl by inject()

    suspend fun retrieveUserData(): User {
        return userRepository.retrieveUserData()
    }

    suspend fun getAccessToken(): String {
        return userRepository.getAccessToken()
    }

    suspend fun setAccessToken(token: String) {
        userRepository.setAccessToken(token)
    }

    suspend fun setUserName(username: String) {
        userRepository.setUsername(username)
    }

    suspend fun setProfilePhotoUrl(url: String) {
        userRepository.setProfilePhotoUrl(url)
    }

    suspend fun setRemember(remember: Boolean) {
        userRepository.setRememberLogin(remember)
    }

    suspend fun logout() {
        userRepository.logOut()
    }
}