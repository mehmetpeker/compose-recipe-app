package com.mehmetpeker.recipe.di

import com.mehmetpeker.recipe.data.UserRepositoryImpl
import com.mehmetpeker.recipe.data.local.preferences.RecipePreferences
import com.mehmetpeker.recipe.data.repository.recipe.RecipeRepositoryImpl
import com.mehmetpeker.recipe.util.RecipeDispatchers
import com.mehmetpeker.recipe.util.SessionManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single { SessionManager() }
    single { UserRepositoryImpl() }
    single { RecipePreferences(androidContext()) }
    single { RecipeDispatchers }
    factory { RecipeRepositoryImpl(get()) }
}