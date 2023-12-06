package com.mehmetpeker.recipe.util

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.request
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess

suspend inline fun <reified T : Any> HttpClient.safeRequest(
    block: HttpRequestBuilder.() -> Unit,
): ApiResult<T> =
    try {
        val response = request { block() }
        if (response.status.isSuccess()) {
            ApiSuccess(response.body())
        } else {
            ApiError(
                code = response.status.value,
                message = response.bodyAsText(),
                errorBody = response.bodyAsText()
            )
        }
    } catch (exception: HttpExceptions) {
        ApiError(
            exception.response.status.value,
            exception.message,
            exception.errorStringResourceId,
            exception.response.bodyAsText()
        )
    } catch (e: Exception) {
        ApiError(throwable = e)
    } catch (e: Throwable) {
        ApiError(throwable = e)
    }