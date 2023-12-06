package com.mehmetpeker.recipe.util

import androidx.annotation.StringRes

sealed interface ApiResult<T : Any>

class ApiSuccess<T : Any>(val data: T) : ApiResult<T>

//If throwable is not null it means this error is exception
class ApiError<T : Any>(
    val code: Int? = null,
    val message: String? = null,
    @StringRes val messageId: Int? = null,
    val errorBody: String? = null,
    val throwable: Throwable? = null
) :
    ApiResult<T>