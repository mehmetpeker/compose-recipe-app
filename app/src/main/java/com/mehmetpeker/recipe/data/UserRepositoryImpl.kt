package com.mehmetpeker.recipe.data

import com.mehmetpeker.recipe.data.entity.ErrorResponseBody
import com.mehmetpeker.recipe.data.entity.user.deletePhotoResponse.DeleteProfilePhotoResponse
import com.mehmetpeker.recipe.data.entity.user.uploadProfilePhoto.UploadProfilePhotoResponse
import com.mehmetpeker.recipe.data.entity.user.userDetail.UserDetailResponse
import com.mehmetpeker.recipe.data.local.preferences.RecipePreferences
import com.mehmetpeker.recipe.domain.repository.UserRepository
import com.mehmetpeker.recipe.util.ApiError
import com.mehmetpeker.recipe.util.ApiResult
import com.mehmetpeker.recipe.util.ApiSuccess
import com.mehmetpeker.recipe.util.User
import com.mehmetpeker.recipe.util.safeRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.url
import io.ktor.client.statement.bodyAsText
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.isSuccess
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.io.File

class UserRepositoryImpl(private val httpClient: HttpClient) : UserRepository, KoinComponent {
    private val preferences: RecipePreferences by inject()

    override suspend fun retrieveUserData(): User {
        return User(
            username = getUsername(),
            profilePhotoUrl = getProfilePhotoUrl(),
            accessToken = getAccessToken(),
            isRemembered = getRememberLogin()
        )
    }

    override suspend fun setAccessToken(token: String) {
        preferences.accessToken = token
    }

    override suspend fun getAccessToken(): String {
        return preferences.accessToken ?: ""
    }

    override suspend fun setUsername(username: String) {
        preferences.userName = username
    }

    override suspend fun getUsername(): String {
        return preferences.userName ?: ""
    }

    override suspend fun setEmail(email: String) {
        preferences.email = email
    }

    override suspend fun getEmail(): String {
        return preferences.email ?: ""
    }

    override suspend fun setProfilePhotoUrl(url: String) {
        preferences.profilePhotoUrl = url
    }

    override suspend fun getProfilePhotoUrl(): String {
        return preferences.profilePhotoUrl ?: ""
    }

    override suspend fun setRememberLogin(remember: Boolean) {
        preferences.rememberLogin = remember
    }

    override suspend fun getRememberLogin(): Boolean {
        return preferences.rememberLogin ?: false
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

    override suspend fun getProfileInformation(): ApiResult<UserDetailResponse> {
        return httpClient.safeRequest {
            method = HttpMethod.Get
            url("api/user/get-user-info")
        }
    }

    override suspend fun uploadProfilePhoto(file: File): ApiResult<UploadProfilePhotoResponse> {
        return try {
            val response = httpClient.submitFormWithBinaryData(
                url = "api/user/add-photo",
                formData = formData {
                    append("File", file.readBytes(), Headers.build {
                        append(HttpHeaders.ContentType, "image/png")
                        append(HttpHeaders.ContentDisposition, "filename=\"user_image.png\"")
                    })
                }
            )
            if (response.status.isSuccess()) {
                ApiSuccess(response.body())
            } else {
                val errorResponseBody: ErrorResponseBody? = try {
                    Json.decodeFromString(response.bodyAsText())
                } catch (e: Exception) {
                    null
                }
                ApiError(
                    errorBody = errorResponseBody,
                )
            }
        } catch (e: Exception) {
            ApiError()
        }
    }

    override suspend fun deleteProfilePhoto(photoId: Int): ApiResult<DeleteProfilePhotoResponse> {
        return httpClient.safeRequest {
            method = HttpMethod.Delete
            url("/api/user/delete-photo/$photoId")
        }
    }
}