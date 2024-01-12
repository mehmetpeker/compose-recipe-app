package com.mehmetpeker.recipe.presentation.main.screens.profile

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.navOptions
import coil.compose.SubcomposeAsyncImage
import com.mehmetpeker.recipe.R
import com.mehmetpeker.recipe.base.BaseScreen
import com.mehmetpeker.recipe.base.EdgeToEdgeScaffold
import com.mehmetpeker.recipe.common.CircularProgressScreen
import com.mehmetpeker.recipe.common.RecipeRoundedButton
import com.mehmetpeker.recipe.common.RecipeRoundedButtonType
import com.mehmetpeker.recipe.data.entity.recipe.getRecipe.Recipe
import com.mehmetpeker.recipe.designsystem.RecipeTopAppBar
import com.mehmetpeker.recipe.designsystem.theme.RecipeFontFamily
import com.mehmetpeker.recipe.designsystem.theme.RoundedCornerShape10Percent
import com.mehmetpeker.recipe.designsystem.theme.md_theme_light_primary
import com.mehmetpeker.recipe.util.NavArgumentConstants
import com.mehmetpeker.recipe.util.RouteConstants
import com.mehmetpeker.recipe.util.extension.toFile
import org.koin.androidx.compose.koinViewModel
import java.io.File

@Composable
fun ProfileScreen(
    mainNavController: NavController,
    nestedNavController: NavController,
    profileViewModel: ProfileViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            val selectedFile = uri?.toFile(context.contentResolver)
            profileViewModel.onImageSelected(selectedFile)

        }

    val profileUiState by profileViewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect("GetUserInformation") {
        profileViewModel.getProfileInformation()
    }
    BaseScreen(profileViewModel, mainNavController) {
        ProfileScreenContent(
            profileUiState,
            onNavigationClick = {
                nestedNavController.popBackStack()
            },
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
            },
            onUploadPhotoClick = {
                launcher.launch(
                    PickVisualMediaRequest(
                        mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                    )
                )
            },
            onDeletePhotoClick = {
                profileViewModel.deleteUserPhoto()
            }, onLogOutClick = {
                profileViewModel.logOut()
                mainNavController.navigate(
                    RouteConstants.ROUTE_ONBOARDING,
                    navOptions = navOptions {
                        launchSingleTop = true
                        popUpTo(RouteConstants.ROUTE_ONBOARDING) {
                            inclusive = true
                        }
                    })
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
    onUpdatePasswordClick: () -> Unit = {},
    onUploadPhotoClick: () -> Unit = {},
    onDeletePhotoClick: () -> Unit = {},
    onLogOutClick: () -> Unit = {},
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
                emptyProfileUiState -> CircularProgressScreen()

                else -> ProfileSuccessContent(
                    modifier = Modifier.fillMaxSize(),
                    uiState = uiState,
                    onRecipeClick = onRecipeClick,
                    onUpdatePasswordClick = onUpdatePasswordClick,
                    onUploadPhotoClick = onUploadPhotoClick,
                    onDeletePhotoClick = onDeletePhotoClick,
                    onLogOutClick = onLogOutClick
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
    onDeletePhotoClick: () -> Unit = {},
    onRecipeClick: (Recipe?) -> Unit = {},
    onUpdatePasswordClick: () -> Unit = {},
    onLogOutClick: () -> Unit = {}

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
            profilePhotoUrl = uiState.profilePhotoUrls.firstOrNull()?.url ?: "",
            onUploadPhotoClick = onUploadPhotoClick,
            onDeletePhotoClick = onDeletePhotoClick
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Kullanıcı Adı", style = MaterialTheme.typography.titleMedium)
            Text(text = uiState.userName)
            Text(text = "Mail Adresi", style = MaterialTheme.typography.titleMedium)
            Text(text = uiState.userEmail)
            RecipeRoundedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(horizontal = 16.dp),
                type = RecipeRoundedButtonType.Primary,
                onClick = onLogOutClick,
            ) {
                Text(
                    text = "Çıkış Yap",
                    style = TextStyle(
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        fontFamily = RecipeFontFamily.poppinsFamily
                    )
                )
            }
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
        if (uiState.userRecipes.isEmpty()) {
            Column(
                Modifier
                    .padding(horizontal = 24.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Henüz hiçbir tarif paylaşmadınız :(",
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Tariflerinizi görüntüleyebilmek için en az 1 tarif paylaşmalısınız",
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(top = 8.dp),
                    textAlign = TextAlign.Center
                )
            }
        } else {
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
    onDeletePhotoClick: () -> Unit = {},
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

            IconButton(modifier = Modifier.align(Alignment.TopEnd), onClick = onDeletePhotoClick) {
                Icon(
                    Icons.Default.Delete,
                    tint = md_theme_light_primary,
                    contentDescription = null
                )
            }

        }
    }
}
