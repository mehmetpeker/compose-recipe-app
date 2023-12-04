package com.mehmetpeker.recipe.util

import com.mehmetpeker.recipe.di.appModule
import com.mehmetpeker.recipe.di.networkModule

object KoinModules {
    val modules = listOf(
        appModule,
        networkModule
    )
}