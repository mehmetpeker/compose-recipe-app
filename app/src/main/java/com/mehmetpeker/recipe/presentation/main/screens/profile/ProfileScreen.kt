package com.mehmetpeker.recipe.presentation.main.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.mehmetpeker.recipe.R
import com.mehmetpeker.recipe.base.BaseScreen
import com.mehmetpeker.recipe.base.EdgeToEdgeScaffold
import com.mehmetpeker.recipe.data.entity.recipe.getRecipe.Recipe
import com.mehmetpeker.recipe.designsystem.RecipeTopAppBar
import com.mehmetpeker.recipe.designsystem.theme.RoundedCornerShape10Percent
import com.mehmetpeker.recipe.designsystem.theme.md_theme_light_primary
import com.mehmetpeker.recipe.util.NavArgumentConstants
import com.mehmetpeker.recipe.util.RouteConstants
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
            profileUiState,
            onUpdatePasswordClick = {
                mainNavController.navigate(RouteConstants.ROUTE_UPDATE_PASSWORD)
            },
            onRecipeClick = {
                it?.let {
                    mainNavController.navigate(
                        RouteConstants.ROUTE_RECIPE_DETAIL.replace(
                            "{${NavArgumentConstants.RECIPE_ID}}",
                            it.id.toString()
                        )
                    )
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreenContent(
    uiState: ProfileViewModel.UiState,
    onNavigationClick: () -> Unit = {},
    onRecipeClick: (Recipe?) -> Unit = {},
    onUpdatePasswordClick: () -> Unit = {}
) {
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
                emptyProfileUiState -> ProfileSuccessContent(
                    modifier = Modifier.fillMaxSize(),
                    uiState = uiState,
                    onRecipeClick = onRecipeClick,
                    onUpdatePasswordClick = onUpdatePasswordClick
                )

                else -> ProfileSuccessContent(
                    modifier = Modifier.fillMaxSize(),
                    uiState = uiState,
                    onRecipeClick = onRecipeClick,
                    onUpdatePasswordClick = onUpdatePasswordClick
                )
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
    onRecipeClick: (Recipe?) -> Unit = {},
    onUpdatePasswordClick: () -> Unit = {}

) {
    val titles = listOf("Tariflerim")
    var tabIndex by remember { mutableIntStateOf(0) }
    Column(
        modifier = modifier,
    ) {
        TextButton(
            onClick = { onUpdatePasswordClick() },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(text = "Parolamı Güncelle", style = MaterialTheme.typography.titleMedium)
        }
        ProfileImage(
            modifier = Modifier
                .size(120.dp)
                .align(Alignment.CenterHorizontally),
            //profilePhotoUrl = uiState.profilePhotoUrl,
            profilePhotoUrl = "https://pbs.twimg.com/profile_images/1423037075264315404/9Tyzo0Lw_400x400.jpg",
            onUploadPhotoClick = onUploadPhotoClick,
            onChangePhotoClick = onChangePhotoClick
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Kullanıcı Adı", style = MaterialTheme.typography.titleMedium)
            Text(text = "memoli")
            Text(text = "Mail Adresi", style = MaterialTheme.typography.titleMedium)
            Text(text = "mehmetpeker41@gmail.com")
        }

        TabRow(selectedTabIndex = tabIndex) {
            titles.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = tabIndex == index,
                    onClick = { tabIndex = index }
                )
            }
        }
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(all = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(space = 12.dp),
            verticalItemSpacing = 8.dp
        ) {
            items(uiState.userRecipes) {
                ProfileRecipeItem(it) {
                    onRecipeClick(it)
                }
            }
        }
    }
}

@Composable
private fun ProfileRecipeItem(recipe: Recipe?, onRecipeClick: (Recipe?) -> Unit = {}) {
    Box(
        Modifier
            .fillMaxWidth()
            .aspectRatio(0.8f)
            .clip(RoundedCornerShape10Percent)
            .clickable { onRecipeClick(recipe) }
    ) {
        SubcomposeAsyncImage(
            model = recipe?.photoUrl,
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.4f))
        )
        Text(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(8.dp),
            text = recipe?.name ?: "-",
            style = MaterialTheme.typography.titleMedium.copy(color = Color.White)
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
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape),
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