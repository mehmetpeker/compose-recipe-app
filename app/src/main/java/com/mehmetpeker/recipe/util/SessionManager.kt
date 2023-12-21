package com.mehmetpeker.recipe.util

import com.mehmetpeker.recipe.data.UserRepositoryImpl
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SessionManager : KoinComponent {
    private val userRepository: UserRepositoryImpl by inject()

    fun retrieveUserData() {}
    suspend fun getAccessToken(): String? {
        return userRepository.getAccessToken()
    }

    suspend fun setAccessToken(token: String) {
        userRepository.setAccessToken(token)
    }

    suspend fun setUserName(token: String) {
        userRepository.setAccessToken(token)
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