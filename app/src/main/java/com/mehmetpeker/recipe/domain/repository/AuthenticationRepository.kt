package com.mehmetpeker.recipe.domain.repository

import com.mehmetpeker.recipe.data.entity.authentication.forgotPassword.ForgotPasswordRequest
import com.mehmetpeker.recipe.data.entity.authentication.forgotPassword.ForgotPasswordResponse
import com.mehmetpeker.recipe.data.entity.authentication.login.LoginRequest
import com.mehmetpeker.recipe.data.entity.authentication.login.LoginResponse
import com.mehmetpeker.recipe.data.entity.authentication.register.RegisterRequest
import com.mehmetpeker.recipe.data.entity.authentication.register.RegisterResponse
import com.mehmetpeker.recipe.data.entity.authentication.resetPassword.ResetPasswordRequest
import com.mehmetpeker.recipe.data.entity.authentication.resetPassword.ResetPasswordResponse
import com.mehmetpeker.recipe.data.entity.authentication.updatePassword.UpdatePasswordRequest
import com.mehmetpeker.recipe.data.entity.authentication.updatePassword.UpdatePasswordResponse
import com.mehmetpeker.recipe.util.ApiResult

interface AuthenticationRepository {

    suspend fun login(body: LoginRequest, isRememberChecked: Boolean): ApiResult<LoginResponse>

    suspend fun register(body: RegisterRequest): ApiResult<RegisterResponse>

    suspend fun forgotPassword(request: ForgotPasswordRequest): ApiResult<ForgotPasswordResponse>

    suspend fun resetPassword(body: ResetPasswordRequest): ApiResult<ResetPasswordResponse>
    suspend fun updatePassword(body: UpdatePasswordRequest): ApiResult<UpdatePasswordResponse>
}