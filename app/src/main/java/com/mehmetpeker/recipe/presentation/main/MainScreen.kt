package com.mehmetpeker.recipe.presentation.main

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mehmetpeker.recipe.R
import com.mehmetpeker.recipe.base.EdgeToEdgeScaffold
import com.mehmetpeker.recipe.data.model.RecipeBottomNavigationScreens
import com.mehmetpeker.recipe.designsystem.theme.md_theme_light_primary
import com.mehmetpeker.recipe.presentation.main.component.RecipeBottomNavigationBar
import com.mehmetpeker.recipe.presentation.main.screens.homepage.HomepageScreen
import com.mehmetpeker.recipe.presentation.main.screens.recipeDetail.RecipeDetailScreen
import com.mehmetpeker.recipe.presentation.main.screens.search.SearchScreen
import com.mehmetpeker.recipe.util.RouteConstants


@Composable
fun HomeScreen(navController: NavController) {
    val nestedNavController = rememberNavController()
    val navBackStackEntry by nestedNavController.currentBackStackEntryAsState()
    EdgeToEdgeScaffold(
        bottomBar = {
            RecipeBottomNavigationBar(
                Modifier.clip(
                    RoundedCornerShape(
                        topStartPercent = 25,
                        topEndPercent = 25
                    )
                ), navController = nestedNavController
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            if (navBackStackEntry?.destination?.route?.equals(RouteConstants.ROUTE_ADD_RECIPE) == false) {
                ExtendedFloatingActionButton(
                    shape = CircleShape,
                    onClick = {
                        RecipeBottomNavigationScreens.AddRecipe.route.let {
                            nestedNavController.navigate(it) {
                                popUpTo(nestedNavController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    containerColor = md_theme_light_primary
                ) {
                    Text(
                        text = stringResource(id = R.string.navigation_add_recipe),
                        style = MaterialTheme.typography.titleSmall
                    )
                    Icon(imageVector = Icons.Filled.Add, contentDescription = null)
                }
            }
        }) {
        NavHost(
            modifier = Modifier
                .padding(it),
            navController = nestedNavController,
            startDestination = RouteConstants.ROUTE_HOMEPAGE
        ) {
            composable(RouteConstants.ROUTE_HOMEPAGE) {
                HomepageScreen(navController = nestedNavController)
            }
            composable(RouteConstants.ROUTE_SEARCH_RECIPE) {
                SearchScreen(navController = nestedNavController)
            }
            composable(RouteConstants.ROUTE_ADD_RECIPE) {
                Text(text = "Add Recipe")
            }
            composable(RouteConstants.ROUTE_FAVORITES) {
                RecipeDetailScreen(navController = navController)
            }
            composable(RouteConstants.ROUTE_PROFILE) {
                Text(text = "Profile")
            }
        }
    }
}
