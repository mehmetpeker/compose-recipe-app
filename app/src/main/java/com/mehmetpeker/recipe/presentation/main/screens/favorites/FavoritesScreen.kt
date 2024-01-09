@file:OptIn(ExperimentalMaterial3Api::class)

package com.mehmetpeker.recipe.presentation.main.screens.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.mehmetpeker.recipe.R
import com.mehmetpeker.recipe.base.BaseScreen
import com.mehmetpeker.recipe.base.EdgeToEdgeScaffold
import com.mehmetpeker.recipe.common.CircularProgressScreen
import com.mehmetpeker.recipe.common.FailScreen
import com.mehmetpeker.recipe.designsystem.RecipeTopAppBar
import com.mehmetpeker.recipe.designsystem.theme.RecipeFontFamily
import com.mehmetpeker.recipe.domain.uimodel.recipe.RecipeItemUiModel
import com.mehmetpeker.recipe.presentation.main.screens.homepage.RecipeItem
import com.mehmetpeker.recipe.util.NavArgumentConstants
import com.mehmetpeker.recipe.util.RouteConstants
import com.mehmetpeker.recipe.util.extension.scaledSp
import org.koin.androidx.compose.koinViewModel

@Composable
fun FavoritesScreen(
    navController: NavController,
    nestedNavController: NavController,
    viewModel: FavoritesViewModel = koinViewModel()
) {
    val likedRecipes by viewModel.likedRecipes.collectAsStateWithLifecycle()
    BaseScreen(viewModel, navController) {
        FavoritesScreenContent(
            onNavigationClick = {
                nestedNavController.popBackStack()
            },
            likedRecipes,
            onRecipeClick = {
                navController.navigate(
                    RouteConstants.ROUTE_RECIPE_DETAIL.replace(
                        "{${NavArgumentConstants.RECIPE_ID}}",
                        it.recipeId
                    )
                )
            },
            onBackToHome = {
                nestedNavController.navigate(RouteConstants.ROUTE_HOME) {
                    launchSingleTop = true
                    popUpTo(RouteConstants.ROUTE_HOME) {
                        inclusive = true
                    }
                }
            }
        )
    }
    LaunchedEffect(Unit) {
        viewModel.getFavoritesRecipe()
    }
}

@Composable
fun FavoritesScreenContent(
    onNavigationClick: () -> Unit = {},
    likedRecipes: List<RecipeItemUiModel>?,
    onRecipeClick: (recipe: RecipeItemUiModel) -> Unit = {},
    onBackToHome: () -> Unit = {}
) {
    EdgeToEdgeScaffold(
        topBar = {
            RecipeTopAppBar(
                titleRes = R.string.favorites,
                navigationIcon = Icons.Default.ArrowBack,
                navigationIconContentDescription = "Navigation icon",
                onNavigationClick = onNavigationClick,
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White),
                navigationTint = Color.Black,
                textColor = Color.Black
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            when (likedRecipes) {
                null -> CircularProgressScreen()
                else -> FavoritesScreenSuccessContent(
                    likedRecipes,
                    onRecipeClick = onRecipeClick,
                    onBackToHome = onBackToHome
                )
            }
        }
    }

}

@Composable
fun FavoritesScreenSuccessContent(
    likedRecipes: List<RecipeItemUiModel>,
    onRecipeClick: (recipe: RecipeItemUiModel) -> Unit = {},
    onBackToHome: () -> Unit = {},
) {
    when {
        likedRecipes.isEmpty() -> FailScreen(
            title = "Henüz hiçbir tarifi beğenmediniz",
            message = "Beğenebileceğiniz tarifleri keşfetmek için tariflere göz atın",
            onButtonClick = onBackToHome
        ) {
            Text(
                text = stringResource(id = R.string.back_to_home_page).uppercase(),
                fontFamily = RecipeFontFamily.poppinsFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 15.scaledSp
            )
        }

        else -> RecipeListSection(recipes = likedRecipes, onRecipeClick = onRecipeClick)
    }
}

@Composable
fun RecipeListSection(
    recipes: List<RecipeItemUiModel>,
    onRecipeClick: (recipe: RecipeItemUiModel) -> Unit = {}
) {
    Column {
        if (recipes.isNotEmpty()) {
            Text(
                modifier = Modifier.padding(start = 12.dp, top = 12.dp),
                text = stringResource(R.string.favorites),
                style = MaterialTheme.typography.titleMedium
            )
        }
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

