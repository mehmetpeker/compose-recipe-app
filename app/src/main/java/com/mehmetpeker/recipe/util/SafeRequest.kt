package com.mehmetpeker.recipe.util

import com.mehmetpeker.recipe.data.entity.ErrorResponseBody
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.request
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import kotlinx.serialization.json.Json

suspend inline fun <reified T : Any> HttpClient.safeRequest(
    block: HttpRequestBuilder.() -> Unit,
): ApiResult<T> =
    try {
        val response = request {
            block()
        }
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
    } catch (exception: HttpExceptions) {
        ApiError(
            messageId = exception.errorStringResourceId,
            errorBody = exception.errorResponseBody,

            )
    } catch (e: Exception) {
        ApiError()
    } catch (e: Throwable) {
        ApiError()
    }
