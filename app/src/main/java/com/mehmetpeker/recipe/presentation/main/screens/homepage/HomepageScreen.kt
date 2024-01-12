@file:OptIn(ExperimentalMaterial3Api::class)

package com.mehmetpeker.recipe.presentation.main.screens.homepage

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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.SubcomposeAsyncImage
import com.mehmetpeker.recipe.R
import com.mehmetpeker.recipe.base.BaseScreen
import com.mehmetpeker.recipe.base.EdgeToEdgeScaffold
import com.mehmetpeker.recipe.common.CircularProgressScreen
import com.mehmetpeker.recipe.designsystem.theme.RoundedCornerShape10Percent
import com.mehmetpeker.recipe.designsystem.theme.md_theme_light_primary
import com.mehmetpeker.recipe.domain.uimodel.recipe.RecipeItemUiModel
import com.mehmetpeker.recipe.presentation.main.component.HomeTopBar
import com.mehmetpeker.recipe.presentation.main.screens.addRecipe.uiModel.CategoriesUiModel
import com.mehmetpeker.recipe.presentation.main.screens.search.EmptySearchScreenContent
import com.mehmetpeker.recipe.util.NavArgumentConstants
import com.mehmetpeker.recipe.util.RouteConstants.ROUTE_RECIPE_DETAIL
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomepageScreen(
    navController: NavController,
    viewModel: HomepageViewModel = koinViewModel(),
    hostNavController: NavHostController
) {
    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect("GetUserData"){
        viewModel.getUserData()
        viewModel.getAllRecipe()
    }
    BaseScreen(viewModel = viewModel, navController = navController) {
        HomepageScreenContent(uiState, onRecipeClick = {
            navController.navigate(
                ROUTE_RECIPE_DETAIL.replace(
                    "{${NavArgumentConstants.RECIPE_ID}}",
                    it.recipeId
                )
            )
        }, onCategorySelected = {
            it.id?.let { selectedId ->
                when (selectedId) {
                    Int.MAX_VALUE -> viewModel.getAllRecipe()
                    else -> viewModel.getAllRecipeByCategory(selectedId.toString())
                }
            }
        })
    }
}

@Composable
fun HomepageScreenContent(
    uiState: HomeUiState,
    onRecipeClick: (recipe: RecipeItemUiModel) -> Unit = {},
    onCategorySelected: (CategoriesUiModel) -> Unit = {}
) {
    EdgeToEdgeScaffold {
        Column(modifier = Modifier.padding(it)) {
            Box(
                modifier = Modifier
                    .background(md_theme_light_primary)
                    .fillMaxWidth()
                    .statusBarsPadding()
            )
            HomeTopBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(bottomStartPercent = 50, bottomEndPercent = 50))
                    .background(md_theme_light_primary)
                    .padding(10.dp),
                profileImageUrl = uiState.user?.profilePhotoUrl,
                username = uiState.user?.username
            )

            if (uiState.categories.isNotEmpty()) {
                Text(
                    modifier = Modifier.padding(start = 12.dp, top = 12.dp),
                    text = stringResource(id = R.string.categories),
                    style = MaterialTheme.typography.titleMedium
                )
                CategoryListSection(
                    modifier = Modifier
                        .height(200.dp),
                    uiState.categories,
                    onCategorySelected = onCategorySelected
                )
            }

            RecipeListSection(
                recipes = uiState.recipes,
                onRecipeClick = onRecipeClick
            )
        }
    }
}

@Composable
fun CategoryListSection(
    modifier: Modifier,
    categories: List<CategoriesUiModel>,
    onCategorySelected: (CategoriesUiModel) -> Unit = {}
) {
    var selectedItem by remember {
        mutableStateOf(categories.first()) // initially, first item is selected
    }
    LazyHorizontalStaggeredGrid(
        rows = StaggeredGridCells.Fixed(4),
        modifier = modifier,
        contentPadding = PaddingValues(all = 12.dp),
        verticalArrangement = Arrangement.spacedBy(space = 8.dp),
        horizontalItemSpacing = 8.dp
    ) {
        itemsIndexed(categories) { index, item ->
            val isSelected = item == selectedItem
            ElevatedFilterChip(
                shape = RoundedCornerShape(50),
                selected = isSelected,
                onClick = {
                    selectedItem = item
                    onCategorySelected(item)
                },
                label = {
                    Text(
                        text = item.name ?: "",
                        fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal,
                        style = MaterialTheme.typography.titleSmall
                    )
                },
                colors = FilterChipDefaults.elevatedFilterChipColors(
                    labelColor = md_theme_light_primary,
                    selectedLabelColor = Color.White,
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    selectedContainerColor = MaterialTheme.colorScheme.primary
                ),
                elevation = FilterChipDefaults.elevatedFilterChipElevation(),
                border = null
            )
        }
    }
}

@Composable
fun RecipeListSection(
    recipes: List<RecipeItemUiModel>? = null,
    onRecipeClick: (recipe: RecipeItemUiModel) -> Unit = {}
) {
    when {
        recipes == null -> CircularProgressScreen()
        recipes.isEmpty() -> EmptySearchScreenContent(modifier = Modifier.fillMaxSize())
        else -> Column {
            Text(
                modifier = Modifier.padding(start = 12.dp, top = 12.dp),
                text = stringResource(R.string.recipes),
                style = MaterialTheme.typography.titleMedium
            )
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(all = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(space = 12.dp),
                verticalItemSpacing = 8.dp
            ) {
                itemsIndexed(recipes) { index, item ->
                    RecipeItem(recipe = item, onRecipeClick = onRecipeClick)
                }
            }
        }


    }
}

@Composable
fun RecipeItem(
    modifier: Modifier = Modifier,
    recipe: RecipeItemUiModel,
    onRecipeClick: (recipe: RecipeItemUiModel) -> Unit = {}
) {
    Box(
        modifier
            .fillMaxWidth()
            .aspectRatio(0.8f)
            .clip(RoundedCornerShape10Percent)
            .clickable { onRecipeClick(recipe) }
    ) {
        SubcomposeAsyncImage(
            model = recipe.recipePhotoUrl,
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
            text = recipe.recipeName,
            style = MaterialTheme.typography.titleMedium.copy(color = Color.White)
        )
    }

}

