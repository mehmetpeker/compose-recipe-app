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
import com.mehmetpeker.recipe.presentation.authentication.login.LoginScreen
import com.mehmetpeker.recipe.presentation.authentication.register.RegisterScreen
import com.mehmetpeker.recipe.presentation.authentication.resetPassword.ResetPasswordScreen
import com.mehmetpeker.recipe.presentation.authentication.updatePassword.UpdatePasswordScreen
import com.mehmetpeker.recipe.presentation.main.HomeScreen
import com.mehmetpeker.recipe.presentation.main.screens.addRecipe.CreateRecipeScreen
import com.mehmetpeker.recipe.presentation.main.screens.recipeDetail.RecipeDetailScreen
import com.mehmetpeker.recipe.presentation.onboarding.OnboardingScreen
import com.mehmetpeker.recipe.util.NavArgumentConstants
import com.mehmetpeker.recipe.util.RouteConstants
import com.mehmetpeker.recipe.util.RouteConstants.ROUTE_FORGOT_PASSWORD
import com.mehmetpeker.recipe.util.RouteConstants.ROUTE_HOME
import com.mehmetpeker.recipe.util.RouteConstants.ROUTE_LOGIN
import com.mehmetpeker.recipe.util.RouteConstants.ROUTE_ONBOARDING
import com.mehmetpeker.recipe.util.RouteConstants.ROUTE_RECIPE_DETAIL
import com.mehmetpeker.recipe.util.RouteConstants.ROUTE_REGISTER
import com.mehmetpeker.recipe.util.RouteConstants.ROUTE_RESET_PASSWORD

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

        composable(RouteConstants.ROUTE_ADD_RECIPE) {
            CreateRecipeScreen(navController)
        }
        composable(
            ROUTE_RECIPE_DETAIL, deepLinks = listOf(navDeepLink {
                uriPattern = "${BuildConfig.BASE_URL}$ROUTE_RECIPE_DETAIL"
                action = Intent.ACTION_VIEW
            }),
            arguments = listOf(
                navArgument(NavArgumentConstants.RECIPE_ID) {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getString(NavArgumentConstants.RECIPE_ID) ?: ""
            RecipeDetailScreen(navController = navController, recipeId = recipeId)
        }
        composable(RouteConstants.ROUTE_UPDATE_PASSWORD) { backStackEntry ->
            UpdatePasswordScreen(navController = navController)
        }
        composable(
            route = ROUTE_RESET_PASSWORD,
            deepLinks = listOf(navDeepLink {
                uriPattern = "${BuildConfig.BASE_URL}$ROUTE_RESET_PASSWORD"
                action = Intent.ACTION_VIEW
            }),
            arguments = listOf(
                navArgument(NavArgumentConstants.EMAIL) {
                    type = NavType.StringType
                    defaultValue = ""
                },
                navArgument(NavArgumentConstants.TOKEN) {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString(NavArgumentConstants.EMAIL) ?: ""
            val token = backStackEntry.arguments?.getString(NavArgumentConstants.TOKEN) ?: ""
            ResetPasswordScreen(navController = navController, email = email, token = token)
        }
    }
}