package com.mehmetpeker.recipe.util

object RouteConstants {
    const val ROUTE_RESET_PASSWORD = "resetPassword/{email}/{token}"
    const val ROUTE_HOME = "home"
    const val ROUTE_HOMEPAGE = "homepage"
    const val ROUTE_LOGIN = "login"
    const val ROUTE_ONBOARDING = "onboarding"
    const val ROUTE_REGISTER = "register"
    const val ROUTE_FORGOT_PASSWORD = "forgotPassword"
    const val ROUTE_SEARCH_RECIPE = "searchRecipe"
    const val ROUTE_ADD_RECIPE = "addRecipe"
    const val ROUTE_FAVORITES = "favorites"
    const val ROUTE_PROFILE = "profile"
    const val ROUTE_UPDATE_PASSWORD = "updatePassword"
    const val ROUTE_RECIPE_DETAIL = "recipeDetail/{${NavArgumentConstants.RECIPE_ID}}"
}