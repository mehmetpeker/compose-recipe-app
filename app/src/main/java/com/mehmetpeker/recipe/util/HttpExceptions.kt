package com.mehmetpeker.recipe.util

import androidx.annotation.StringRes
import com.mehmetpeker.recipe.data.entity.ErrorResponseBody
import io.ktor.client.plugins.ResponseException
import io.ktor.client.statement.HttpResponse

class HttpExceptions(
    response: HttpResponse,
    val cachedResponseText: String,
    @StringRes val errorStringResourceId: Int?,
    val errorResponseBody: ErrorResponseBody?
) : ResponseException(response, cachedResponseText) {
    override val message: String =
        "Status: ${response.status}." + " Failure: ${response.status.description}"
}