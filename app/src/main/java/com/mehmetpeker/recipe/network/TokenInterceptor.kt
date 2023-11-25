package com.mehmetpeker.recipe.network

import com.mehmetpeker.recipe.util.SessionManager
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.net.HttpURLConnection

class TokenInterceptor : Interceptor, KoinComponent {
    private val sessionManager: SessionManager by inject()
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var accessToken = sessionManager.getAccessToken()

        val response = chain.proceed(newRequestWithAccessToken(accessToken, request))

        if (response.code == HttpURLConnection.HTTP_UNAUTHORIZED) {
            synchronized(KtorClient.client) {
                val newAccessToken = sessionManager.getAccessToken()
                if (newAccessToken != accessToken) {
                    return chain.proceed(newRequestWithAccessToken(accessToken, request))
                } else {
                    accessToken = refreshToken()
                    if (accessToken.isNullOrBlank()) {
                        sessionManager.logout()
                        return response
                    }
                    return chain.proceed(newRequestWithAccessToken(accessToken, request))
                }
            }

        }

        return response
    }

    private fun newRequestWithAccessToken(accessToken: String?, request: Request): Request =
        request.newBuilder()
            .header("Authorization", "Bearer $accessToken")
            .build()

    private fun refreshToken(): String? {
        synchronized(this) {
            val refreshToken = sessionManager.getRefreshToken()
            refreshToken?.let {
                return sessionManager.refreshToken(refreshToken)
            } ?: return null
        }
    }
}
