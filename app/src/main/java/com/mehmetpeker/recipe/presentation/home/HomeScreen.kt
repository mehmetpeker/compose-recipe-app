package com.mehmetpeker.recipe.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.navigation.NavController
import com.mehmetpeker.recipe.base.BaseScreen
import com.mehmetpeker.recipe.common.SetStatusBarColor
import com.mehmetpeker.recipe.designsystem.theme.md_theme_light_primary
import com.mehmetpeker.recipe.presentation.home.component.HomeTopBar
import org.koin.androidx.compose.koinViewModel

const val ROUTE_HOME = "home"

@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = koinViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    BaseScreen(viewModel = viewModel, navController = navController) {
        HomeScreenContent(uiState)
    }
}

@Composable
fun HomeScreenContent(uiState: HomeUiState) {
    SetStatusBarColor(color = md_theme_light_primary)
    Scaffold {
        Column(modifier = Modifier.padding(it)) {
            HomeTopBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(bottomStartPercent = 20, bottomEndPercent = 20))
                    .background(md_theme_light_primary),
                profileImageUrl = uiState.user?.profilePhotoUrl,
                username = uiState.user?.username
            )
        }
    }
}