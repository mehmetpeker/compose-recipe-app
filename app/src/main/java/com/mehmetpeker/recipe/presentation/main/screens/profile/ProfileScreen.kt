package com.mehmetpeker.recipe.presentation.main.screens.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.mehmetpeker.recipe.R
import com.mehmetpeker.recipe.base.BaseScreen
import com.mehmetpeker.recipe.base.EdgeToEdgeScaffold
import com.mehmetpeker.recipe.common.CircularProgressScreen
import com.mehmetpeker.recipe.designsystem.RecipeTopAppBar
import com.mehmetpeker.recipe.designsystem.theme.md_theme_light_primary
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen(
    mainNavController: NavController,
    nestedNavController: NavController,
    profileViewModel: ProfileViewModel = koinViewModel()
) {

    val profileUiState by produceState(
        initialValue = emptyProfileUiState
    ) {
        profileViewModel.getProfileInformation()
        value = profileViewModel.uiState.value
    }
    BaseScreen(profileViewModel, mainNavController) {
        ProfileScreenContent(
            profileUiState
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreenContent(uiState: ProfileViewModel.UiState, onNavigationClick: () -> Unit = {}) {
    EdgeToEdgeScaffold(
        topBar = {
            RecipeTopAppBar(
                titleRes = R.string.profile,
                navigationIcon = Icons.Default.ArrowBack,
                navigationIconContentDescription = "Navigation icon",
                onNavigationClick = onNavigationClick,
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White),
                navigationTint = Color.Black,
                textColor = Color.Black
            )
        }
    ) {
        Surface(modifier = Modifier.padding(it)) {
            when (uiState) {
                emptyProfileUiState -> CircularProgressScreen()
                else -> ProfileSuccessContent(uiState = uiState)
            }
        }
    }
}

@Composable
private fun ProfileSuccessContent(
    modifier: Modifier = Modifier,
    uiState: ProfileViewModel.UiState,
    onUploadPhotoClick: () -> Unit = {},
    onChangePhotoClick: () -> Unit = {},
) {
    Column(
        modifier = modifier
    ) {
        ProfileImage(
            modifier = Modifier.align(CenterHorizontally),
            profilePhotoUrl = uiState.profilePhotoUrl,
            onUploadPhotoClick = onUploadPhotoClick,
            onChangePhotoClick = onChangePhotoClick
        )
    }
}

@Composable
private fun ProfileImage(
    modifier: Modifier,
    profilePhotoUrl: String,
    onUploadPhotoClick: () -> Unit = {},
    onChangePhotoClick: () -> Unit = {},
) {
    Box(modifier = modifier) {
        SubcomposeAsyncImage(
            model = profilePhotoUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
            loading = {
                Box(
                    modifier = Modifier.size(24.dp), contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                }
            }
        )
        if (profilePhotoUrl.isEmpty()) {
            IconButton(modifier = Modifier.align(Alignment.Center), onClick = onUploadPhotoClick) {
                Icon(
                    Icons.Default.AddCircle,
                    tint = md_theme_light_primary,
                    contentDescription = null
                )
            }
        } else {

            IconButton(modifier = Modifier.align(Alignment.TopEnd), onClick = onChangePhotoClick) {
                Icon(
                    Icons.Default.Edit,
                    tint = md_theme_light_primary,
                    contentDescription = null
                )
            }

        }
    }
}