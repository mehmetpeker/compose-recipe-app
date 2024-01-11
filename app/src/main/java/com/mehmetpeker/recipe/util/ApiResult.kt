package com.mehmetpeker.recipe.util

import androidx.annotation.StringRes
import com.mehmetpeker.recipe.data.entity.ErrorResponseBody

sealed interface ApiResult<T : Any>

class ApiSuccess<T : Any>(val data: T) : ApiResult<T>

//If throwable is not null it means this error is exception
class ApiError<T : Any>(
    @StringRes val messageId: Int? = null,
    val errorBody: ErrorResponseBody? = null,
    val httpStatusCode: Int? = null
) :
    ApiResult<T>