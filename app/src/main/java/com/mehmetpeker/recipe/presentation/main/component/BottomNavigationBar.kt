package com.mehmetpeker.recipe.presentation.main.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.mehmetpeker.recipe.data.model.RecipeBottomNavigationScreens
import com.mehmetpeker.recipe.designsystem.theme.md_theme_light_primary


@Composable
fun RecipeBottomNavigationBar(
    navController: NavHostController
) {
    val screens = listOf(
        RecipeBottomNavigationScreens.Home,
        RecipeBottomNavigationScreens.Search,
        RecipeBottomNavigationScreens.Favorites,
        RecipeBottomNavigationScreens.Profile,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(md_theme_light_primary)
        ) {
            screens.forEach {
                AddItem(
                    screen = it,
                    currentDestination = currentDestination,
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: RecipeBottomNavigationScreens,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    NavigationBarItem(
        colors = NavigationBarItemDefaults.colors(
            unselectedIconColor = Color.LightGray,
            selectedIconColor = md_theme_light_primary,
            selectedTextColor = Color.White,
            unselectedTextColor = Color.LightGray
        ),
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        },
        icon = {
            Icon(
                imageVector = screen.icon,
                contentDescription = screen.title
            )
        },
        label = {
            Text(
                text = screen.title,
                style = MaterialTheme.typography.labelMedium
            )
        }
    )
}