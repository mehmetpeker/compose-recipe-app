package com.mehmetpeker.recipe.util

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.request
import kotlinx.serialization.SerializationException

suspend inline fun <reified T, reified E> HttpClient.safeRequest(
    block: HttpRequestBuilder.() -> Unit,
): ApiResponse<T, E> =
    try {
        val response = request { block() }
        ApiResponse.Success(response.body())
    } catch (exception: ClientRequestException) {
        ApiResponse.Error.HttpError(
            code = exception.response.status.value,
            errorBody = exception.response.body(),
            errorMessage = "Status Code: ${exception.response.status.value} - API Key Missing",
        )
    } catch (exception: HttpExceptions) {
        ApiResponse.Error.HttpError(
            code = exception.response.status.value,
            errorBody = exception.response.body(),
            errorMessage = exception.message,
        )
    } catch (e: SerializationException) {
        ApiResponse.Error.SerializationError(e.message)
    } catch (e: Exception) {
        ApiResponse.Error.GenericError(e.message)
    }