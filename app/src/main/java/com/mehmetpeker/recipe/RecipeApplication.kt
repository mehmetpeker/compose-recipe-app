package com.mehmetpeker.recipe

import android.app.Application
import com.mehmetpeker.recipe.util.KoinModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class RecipeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@RecipeApplication)
            modules(KoinModules.modules)
        }
    }
}