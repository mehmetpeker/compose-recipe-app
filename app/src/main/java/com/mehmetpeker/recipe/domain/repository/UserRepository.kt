package com.mehmetpeker.recipe.domain.repository

import com.mehmetpeker.recipe.data.entity.user.deletePhotoResponse.DeleteProfilePhotoResponse
import com.mehmetpeker.recipe.data.entity.user.uploadProfilePhoto.UploadProfilePhotoResponse
import com.mehmetpeker.recipe.data.entity.user.userDetail.UserDetailResponse
import com.mehmetpeker.recipe.util.ApiResult
import com.mehmetpeker.recipe.util.User
import java.io.File

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

    suspend fun getProfileInformation(): ApiResult<UserDetailResponse>
    suspend fun uploadProfilePhoto(file: File): ApiResult<UploadProfilePhotoResponse>
    suspend fun deleteProfilePhoto(photoId: Int): ApiResult<DeleteProfilePhotoResponse>
}