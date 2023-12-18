package com.mehmetpeker.recipe.util

import com.mehmetpeker.recipe.di.appModule
import com.mehmetpeker.recipe.di.networkModule
import com.mehmetpeker.recipe.presentation.authentication.login.loginModule
import com.mehmetpeker.recipe.presentation.authentication.register.registerModule

object KoinModules {
    val modules = listOf(
        appModule,
        networkModule,
        loginModule,
        registerModule
    )
}