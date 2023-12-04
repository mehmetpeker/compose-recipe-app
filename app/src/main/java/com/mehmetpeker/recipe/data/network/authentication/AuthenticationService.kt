package com.mehmetpeker.recipe.data.network.authentication

import com.mehmetpeker.recipe.data.entity.authentication.login.LoginRequest
import com.mehmetpeker.recipe.data.entity.authentication.login.LoginResponse
import com.mehmetpeker.recipe.data.entity.authentication.register.RegisterRequest
import com.mehmetpeker.recipe.data.entity.authentication.register.RegisterResponse
import com.mehmetpeker.recipe.data.entity.authentication.resetPassword.ResetPasswordRequest
import com.mehmetpeker.recipe.data.entity.authentication.updatePassword.UpdatePasswordRequest
import com.mehmetpeker.recipe.network.KtorClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType

class AuthenticationService {
    suspend fun login(body: LoginRequest): LoginResponse {
        val loginResponse = KtorClient.client.post {
            url("/api/account/login")
            setBody(body)

        }.body<LoginResponse>()
        return loginResponse
    }

    suspend fun register(body: RegisterRequest): RegisterResponse {
        val registerResponse = KtorClient.client.post {
            url("/api/account/register")
            setBody(body)

        }.body<RegisterResponse>()
        return registerResponse
    }

    suspend fun forgotPassword(email: String): String {
        val forgotPasswordResponse = KtorClient.client.post {
            contentType(ContentType.Text.Plain)
            url("/api/account/forgot_password")
            setBody(email)

        }.body<String>()
        return forgotPasswordResponse
    }

    suspend fun resetPassword(body: ResetPasswordRequest): String {
        val resetPasswordResponse = KtorClient.client.post {
            url("/api/account/reset_password")
            setBody(body)

        }.body<String>()
        return resetPasswordResponse
    }

    suspend fun updatePassword(body: UpdatePasswordRequest): String {
        val updatePasswordResponse = KtorClient.client.post {
            url("/api/account/update_password")
            setBody(body)

        }.body<String>()
        return updatePasswordResponse
    }
}