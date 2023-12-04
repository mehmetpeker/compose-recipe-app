package com.mehmetpeker.recipe.di

import com.mehmetpeker.recipe.data.network.authentication.AuthenticationService
import com.mehmetpeker.recipe.network.SimpleLoggingInterceptor
import com.mehmetpeker.recipe.network.TokenInterceptor
import org.koin.dsl.module

val networkModule = module {
    single { SimpleLoggingInterceptor() }
    single { TokenInterceptor() }
    factory { AuthenticationService() }
}