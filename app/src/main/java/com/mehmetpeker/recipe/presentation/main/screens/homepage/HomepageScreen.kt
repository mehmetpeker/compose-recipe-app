package com.mehmetpeker.recipe.presentation.main.screens.homepage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mehmetpeker.recipe.base.BaseScreen
import com.mehmetpeker.recipe.base.EdgeToEdgeScaffold
import com.mehmetpeker.recipe.designsystem.theme.ChangeSystemBarColor
import com.mehmetpeker.recipe.designsystem.theme.md_theme_light_primary
import com.mehmetpeker.recipe.presentation.main.component.HomeTopBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomepageScreen(navController: NavController, viewModel: HomepageViewModel = koinViewModel()) {
    ChangeSystemBarColor(md_theme_light_primary)
    val uiState by viewModel.uiState.collectAsState()
    BaseScreen(viewModel = viewModel, navController = navController) {
        HomepageScreenContent(uiState)
    }
}

@Composable
fun HomepageScreenContent(uiState: HomeUiState) {
    EdgeToEdgeScaffold {
        Column(modifier = Modifier.padding(it)) {
            HomeTopBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(bottomStartPercent = 50, bottomEndPercent = 50))
                    .background(md_theme_light_primary)
                    .padding(10.dp),
                profileImageUrl = uiState.user?.profilePhotoUrl,
                username = uiState.user?.username
            )
        }
    }
}