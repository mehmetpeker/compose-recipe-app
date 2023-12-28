package com.mehmetpeker.recipe.data.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.mehmetpeker.recipe.R
import com.mehmetpeker.recipe.RecipeApplication
import com.mehmetpeker.recipe.util.RouteConstants.ROUTE_ADD_RECIPE
import com.mehmetpeker.recipe.util.RouteConstants.ROUTE_FAVORITES
import com.mehmetpeker.recipe.util.RouteConstants.ROUTE_HOMEPAGE
import com.mehmetpeker.recipe.util.RouteConstants.ROUTE_PROFILE
import com.mehmetpeker.recipe.util.RouteConstants.ROUTE_SEARCH_RECIPE

sealed class RecipeBottomNavigationScreens(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    data object Home : RecipeBottomNavigationScreens(
        route = ROUTE_HOMEPAGE,
        title = RecipeApplication.getAppContext().getString(R.string.navigation_home),
        icon = Icons.Default.Home
    )

    data object Search : RecipeBottomNavigationScreens(
        route = ROUTE_SEARCH_RECIPE,
        title = RecipeApplication.getAppContext().getString(R.string.navigation_search),
        icon = Icons.Default.Search
    )

    data object AddRecipe : RecipeBottomNavigationScreens(
        route = ROUTE_ADD_RECIPE,
        title = RecipeApplication.getAppContext().getString(R.string.navigation_add_recipe),
        icon = Icons.Default.AddCircle
    )

    data object Favorites : RecipeBottomNavigationScreens(
        route = ROUTE_FAVORITES,
        title = RecipeApplication.getAppContext().getString(R.string.navigation_favorites),
        icon = Icons.Default.Favorite
    )

    data object Profile : RecipeBottomNavigationScreens(
        route = ROUTE_PROFILE,
        title = RecipeApplication.getAppContext().getString(R.string.navigation_profile),
        icon = Icons.Default.Person
    )
}
