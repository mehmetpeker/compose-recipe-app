package com.mehmetpeker.recipe.util

import com.mehmetpeker.recipe.di.appModule
import com.mehmetpeker.recipe.di.networkModule
import com.mehmetpeker.recipe.presentation.authentication.forgotPassword.forgotPasswordModule
import com.mehmetpeker.recipe.presentation.authentication.login.loginModule
import com.mehmetpeker.recipe.presentation.authentication.register.registerModule
import com.mehmetpeker.recipe.presentation.authentication.resetPassword.resetPasswordModule
import com.mehmetpeker.recipe.presentation.main.screens.addRecipe.createRecipeModule
import com.mehmetpeker.recipe.presentation.main.screens.favorites.favoritesModule
import com.mehmetpeker.recipe.presentation.main.screens.homepage.homeModule
import com.mehmetpeker.recipe.presentation.main.screens.profile.profileModule
import com.mehmetpeker.recipe.presentation.main.screens.recipeDetail.recipeDetailModule
import com.mehmetpeker.recipe.presentation.main.screens.search.searchModule
import com.mehmetpeker.recipe.presentation.onboarding.onboardingModule

object KoinModules {
    val modules = listOf(
        appModule,
        networkModule,
        loginModule,
        registerModule,
        forgotPasswordModule,
        resetPasswordModule,
        homeModule,
        searchModule,
        onboardingModule,
        recipeDetailModule,
        createRecipeModule,
        favoritesModule,
        profileModule
    )
}