package com.mehmetpeker.recipe.di

import com.mehmetpeker.recipe.data.network.authentication.AuthenticationServiceImpl
import com.mehmetpeker.recipe.network.KtorClient
import com.mehmetpeker.recipe.network.SimpleLoggingInterceptor
import com.mehmetpeker.recipe.network.TokenInterceptor
import org.koin.dsl.module

val networkModule = module {
    single { SimpleLoggingInterceptor() }
    single { TokenInterceptor() }
    single { KtorClient.client }
    factory { AuthenticationServiceImpl(get()) }
}