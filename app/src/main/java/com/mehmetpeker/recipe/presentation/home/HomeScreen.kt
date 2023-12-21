package com.mehmetpeker.recipe.presentation.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

const val ROUTE_HOME = "home"

@Composable
fun HomeScreen(navController: NavHostController) {
    HomeScreenContent()
}

@Composable
fun HomeScreenContent() {
    Scaffold {
        Text(modifier = Modifier.padding(it), text = "Home Screen")
    }
}