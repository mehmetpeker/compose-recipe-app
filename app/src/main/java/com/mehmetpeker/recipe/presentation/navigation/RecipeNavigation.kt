package com.mehmetpeker.recipe.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mehmetpeker.recipe.presentation.authentication.login.LoginScreen
import com.mehmetpeker.recipe.presentation.onboarding.OnboardingScreen

@Composable
fun RecipeNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "onboarding"
    ) {

        composable("onboarding") {
            OnboardingScreen(navController)
        }
        composable("login") {
            LoginScreen(navController)
        }


    }

}