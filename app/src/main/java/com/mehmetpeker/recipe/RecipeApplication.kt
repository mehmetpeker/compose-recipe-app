package com.mehmetpeker.recipe

import android.app.Application
import android.content.Context
import com.mehmetpeker.recipe.util.KoinModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class RecipeApplication : Application() {
    companion object {
        private lateinit var instance: RecipeApplication

        fun getAppContext(): Context {
            return instance.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin {
            androidLogger()
            androidContext(this@RecipeApplication)
            modules(KoinModules.modules)
        }
    }
}