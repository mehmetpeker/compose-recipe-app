package com.mehmetpeker.recipe.presentation.navigation

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.mehmetpeker.recipe.BuildConfig
import com.mehmetpeker.recipe.presentation.authentication.forgotPassword.ForgotPasswordScreen
import com.mehmetpeker.recipe.presentation.authentication.forgotPassword.ROUTE_FORGOT_PASSWORD
import com.mehmetpeker.recipe.presentation.authentication.login.LoginScreen
import com.mehmetpeker.recipe.presentation.authentication.login.ROUTE_LOGIN
import com.mehmetpeker.recipe.presentation.authentication.register.ROUTE_REGISTER
import com.mehmetpeker.recipe.presentation.authentication.register.RegisterScreen
import com.mehmetpeker.recipe.presentation.authentication.resetPassword.ROUTE_RESET_PASSWORD
import com.mehmetpeker.recipe.presentation.authentication.resetPassword.ResetPasswordScreen
import com.mehmetpeker.recipe.presentation.home.HomeScreen
import com.mehmetpeker.recipe.presentation.home.ROUTE_HOME
import com.mehmetpeker.recipe.presentation.onboarding.OnboardingScreen
import com.mehmetpeker.recipe.presentation.onboarding.ROUTE_ONBOARDING

@Composable
fun RecipeNavigation(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = ROUTE_ONBOARDING
    ) {

        composable(ROUTE_ONBOARDING) {
            OnboardingScreen(navController)
        }
        composable(ROUTE_LOGIN) {
            LoginScreen(navController)
        }
        composable(ROUTE_REGISTER) {
            RegisterScreen(navController)
        }
        composable(ROUTE_HOME) {
            HomeScreen(navController)
        }
        composable(ROUTE_FORGOT_PASSWORD) {
            ForgotPasswordScreen(navController)
        }
        composable(
            route = ROUTE_RESET_PASSWORD,
            deepLinks = listOf(navDeepLink {
                uriPattern = "${BuildConfig.BASE_URL}$ROUTE_RESET_PASSWORD"
                action = Intent.ACTION_VIEW
            }),
            arguments = listOf(
                navArgument("email") {
                    type = NavType.StringType
                    defaultValue = ""
                },
                navArgument("token") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            val token = backStackEntry.arguments?.getString("token") ?: ""
            ResetPasswordScreen(navController = navController, email = email, token = token)
        }
    }
}