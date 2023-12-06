package com.mehmetpeker.recipe.data.network.authentication

import com.mehmetpeker.recipe.data.entity.authentication.login.LoginRequest
import com.mehmetpeker.recipe.data.entity.authentication.login.LoginResponse
import com.mehmetpeker.recipe.data.entity.authentication.register.RegisterRequest
import com.mehmetpeker.recipe.data.entity.authentication.register.RegisterResponse
import com.mehmetpeker.recipe.data.entity.authentication.resetPassword.ResetPasswordRequest
import com.mehmetpeker.recipe.data.entity.authentication.updatePassword.UpdatePasswordRequest
import com.mehmetpeker.recipe.util.ApiResult
import com.mehmetpeker.recipe.util.safeRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType

class AuthenticationServiceImpl(private val client: HttpClient) : AuthenticationService {

    override suspend fun login(body: LoginRequest): ApiResult<LoginResponse> = client.safeRequest {
        url("/api/account/login")
        setBody(body)
    }

    override suspend fun register(body: RegisterRequest): ApiResult<RegisterResponse> =
        client.safeRequest {
            url("/api/account/register")
            setBody(body)
        }

    override suspend fun forgotPassword(email: String): ApiResult<String> = client.safeRequest {
        method = HttpMethod.Post
        contentType(ContentType.Text.Plain)
        url("/api/account/forgot_password")
        setBody(email)
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