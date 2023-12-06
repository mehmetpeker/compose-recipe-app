package com.mehmetpeker.recipe.data.network.authentication

import com.mehmetpeker.recipe.data.entity.authentication.login.LoginRequest
import com.mehmetpeker.recipe.data.entity.authentication.login.LoginResponse
import com.mehmetpeker.recipe.data.entity.authentication.register.RegisterRequest
import com.mehmetpeker.recipe.data.entity.authentication.register.RegisterResponse
import com.mehmetpeker.recipe.data.entity.authentication.resetPassword.ResetPasswordRequest
import com.mehmetpeker.recipe.data.entity.authentication.updatePassword.UpdatePasswordRequest
import com.mehmetpeker.recipe.util.ApiResult

interface AuthenticationService {

    suspend fun login(body: LoginRequest): ApiResult<LoginResponse>

    suspend fun register(body: RegisterRequest): ApiResult<RegisterResponse>

    suspend fun forgotPassword(email: String): ApiResult<String>

    suspend fun resetPassword(body: ResetPasswordRequest): ApiResult<String>
    suspend fun updatePassword(body: UpdatePasswordRequest): ApiResult<String>
}