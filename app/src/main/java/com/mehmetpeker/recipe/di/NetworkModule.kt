package com.mehmetpeker.recipe.di

import com.mehmetpeker.recipe.data.repository.authentication.AuthenticationRepositoryImpl
import com.mehmetpeker.recipe.network.TokenInterceptor
import com.mehmetpeker.recipe.network.client
import org.koin.dsl.module

val networkModule = module {
    single { TokenInterceptor() }
    single { client }
    factory { AuthenticationRepositoryImpl(get(), get()) }
}