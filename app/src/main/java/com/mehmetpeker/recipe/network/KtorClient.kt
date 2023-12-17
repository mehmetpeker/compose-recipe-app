package com.mehmetpeker.recipe.network

import android.util.Log
import com.mehmetpeker.recipe.BuildConfig
import com.mehmetpeker.recipe.R
import com.mehmetpeker.recipe.util.HttpExceptions
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.accept
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import java.io.IOException

object KtorClient {
    val client = HttpClient(OkHttp) {
        expectSuccess = true
        engine {
            config {
                followRedirects(true)
            }
            addInterceptor(SimpleLoggingInterceptor())
            addInterceptor(TokenInterceptor())
        }
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
                        HttpStatusCode.Unauthorized -> R.string.a_error
                        HttpStatusCode.Forbidden -> R.string.a_error
                        HttpStatusCode.NotFound -> R.string.a_error
                        HttpStatusCode.UpgradeRequired -> R.string.a_error
                        HttpStatusCode.RequestTimeout -> R.string.a_error
                        in HttpStatusCode.InternalServerError..HttpStatusCode.GatewayTimeout -> R.string.a_error
                        else -> R.string.generic_error
                    }

                    throw HttpExceptions(
                        response = response,
                        cachedResponseText = response.bodyAsText(),
                        errorStringResourceId = errorResourceId
                    )
                }
            }
        }
        install(HttpRequestRetry) {
            maxRetries = 5
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
        }
        // Timeout
        install(HttpTimeout) {
            val requestTimeoutMillis = 15000L
            connectTimeoutMillis = requestTimeoutMillis
            socketTimeoutMillis = requestTimeoutMillis
        }
        // Apply to all requests
        defaultRequest {
            url(urlString = BuildConfig.BASE_URL)
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }
}
