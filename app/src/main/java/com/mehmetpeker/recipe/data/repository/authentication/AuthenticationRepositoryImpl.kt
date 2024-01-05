package com.mehmetpeker.recipe.data.repository.authentication

import com.mehmetpeker.recipe.data.entity.authentication.forgotPassword.ForgotPasswordRequest
import com.mehmetpeker.recipe.data.entity.authentication.login.LoginRequest
import com.mehmetpeker.recipe.data.entity.authentication.login.LoginResponse
import com.mehmetpeker.recipe.data.entity.authentication.register.RegisterRequest
import com.mehmetpeker.recipe.data.entity.authentication.register.RegisterResponse
import com.mehmetpeker.recipe.data.entity.authentication.resetPassword.ResetPasswordRequest
import com.mehmetpeker.recipe.data.entity.authentication.updatePassword.UpdatePasswordRequest
import com.mehmetpeker.recipe.domain.repository.AuthenticationRepository
import com.mehmetpeker.recipe.util.ApiResult
import com.mehmetpeker.recipe.util.ApiSuccess
import com.mehmetpeker.recipe.util.SessionManager
import com.mehmetpeker.recipe.util.safeRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType

class AuthenticationRepositoryImpl(
    private val client: HttpClient,
    private val sessionManager: SessionManager
) : AuthenticationRepository {

    override suspend fun login(
        body: LoginRequest,
        isRememberChecked: Boolean
    ): ApiResult<LoginResponse> {
        val response = client.safeRequest<LoginResponse> {
            method = HttpMethod.Post
            url("api/account/login")
            setBody(body)
        }.also {
            if (it is ApiSuccess) {
                it.data.let { response ->
                    sessionManager.apply {
                        setAccessToken(response.token ?: "")
                        setUserName(response.username ?: "")
                        setProfilePhotoUrl(response.photoUrl ?: "")
                        setRemember(isRememberChecked)
                    }
                }
            }
        }
        return response
    }


    override suspend fun register(body: RegisterRequest): ApiResult<RegisterResponse> =
        client.safeRequest {
            method = HttpMethod.Post
            url("/api/account/register")
            setBody(body)
        }

    override suspend fun forgotPassword(request: ForgotPasswordRequest): ApiResult<String> =
        client.safeRequest {
            method = HttpMethod.Post
            contentType(ContentType.Text.Plain)
            url("/api/account/forgot_password")
            setBody(request)
        }

    override suspend fun resetPassword(body: ResetPasswordRequest): ApiResult<String> =
        client.safeRequest {
            url("/api/account/reset_password")
            setBody(body)
        }

    override suspend fun updatePassword(body: UpdatePasswordRequest): ApiResult<String> =
        client.safeRequest {
            url("/api/account/update_password")
            setBody(body)
        }
}