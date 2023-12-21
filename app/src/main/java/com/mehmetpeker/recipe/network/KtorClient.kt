package com.mehmetpeker.recipe.network

import android.util.Log
import com.mehmetpeker.recipe.BuildConfig
import com.mehmetpeker.recipe.R
import com.mehmetpeker.recipe.data.entity.ErrorResponseBody
import com.mehmetpeker.recipe.util.HttpExceptions
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.api.Send
import io.ktor.client.plugins.api.createClientPlugin
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import java.io.IOException


val client = HttpClient(OkHttp) {
    engine {
        config {
            followRedirects(true)
        }
        addInterceptor(TokenInterceptor())
    }
    install(createClientPlugin("fix") {
        on(Send) { request ->
            request.headers.remove("Accept-Charset")
            this.proceed(request)
        }
    })
    HttpResponseValidator {

        validateResponse { response ->

            val startTime = System.nanoTime()
            // Process the response as per your requirement
            val statusCode = response.status.value

            val endTime = System.nanoTime()
            val latencyInMillis = (endTime - startTime) / 1_000_000

            // Log the latency or use it as per your requirement
            Log.d("response_time", "API Latency: $latencyInMillis ms: ")

            // Continue processing the response

            if (!response.status.isSuccess()) {
                val errorResourceId = when (response.status) {
                    HttpStatusCode.Unauthorized -> R.string.error_unauthorized
                    HttpStatusCode.Forbidden -> R.string.a_error
                    HttpStatusCode.NotFound -> R.string.a_error
                    HttpStatusCode.UpgradeRequired -> R.string.a_error
                    HttpStatusCode.RequestTimeout -> R.string.a_error
                    in HttpStatusCode.InternalServerError..HttpStatusCode.GatewayTimeout -> R.string.a_error
                    else -> R.string.generic_error
                }
                val responseText = response.bodyAsText()
                val errorResponseBody: ErrorResponseBody? = try {
                    Json.decodeFromString(responseText)
                } catch (e: Exception) {
                    null
                }
                throw HttpExceptions(
                    response = response,
                    cachedResponseText = responseText,
                    errorStringResourceId = errorResourceId,
                    errorResponseBody = errorResponseBody
                )
            }
        }
    }

    install(HttpRequestRetry) {
        maxRetries = 1
        retryIf { request, response ->
            !response.status.isSuccess()
        }
        retryOnExceptionIf { request, cause ->
            cause is IOException
        }
        delayMillis { retry ->
            retry * 3000L
        } // retries in 3, 6, 9, etc. seconds
    }
    // Caching
    install(HttpCache)

    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
        })
    }
    // Logging
    install(Logging) {
        logger = Logger.DEFAULT
        level = LogLevel.ALL
        logger = object : Logger {
            override fun log(message: String) {
                Log.d("RecipeClient", message)
            }
        }
    }
    install(HttpTimeout) {
        val requestTimeoutMillis = 15000L
        connectTimeoutMillis = requestTimeoutMillis
        socketTimeoutMillis = requestTimeoutMillis
    }
    defaultRequest {
        contentType(ContentType.Application.Json)
        url(urlString = BuildConfig.BASE_URL)
    }
}
