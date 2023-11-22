package com.mehmetpeker.recipe.di

import com.mehmetpeker.recipe.util.SessionManager
import org.koin.dsl.module

val appModule = module {
    single { SessionManager() }
}