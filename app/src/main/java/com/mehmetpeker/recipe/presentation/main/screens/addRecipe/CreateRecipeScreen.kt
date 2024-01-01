@file:OptIn(ExperimentalMaterial3Api::class)

package com.mehmetpeker.recipe.presentation.main.screens.addRecipe

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.mehmetpeker.recipe.R
import com.mehmetpeker.recipe.base.EdgeToEdgeScaffold
import com.mehmetpeker.recipe.designsystem.RecipeTextField
import com.mehmetpeker.recipe.designsystem.theme.RoundedCornerShape10Percent
import org.koin.androidx.compose.koinViewModel

@Composable
fun CreateRecipeScreen(
    navController: NavController,
    createRecipeViewModel: CreateRecipeViewModel = koinViewModel()
) {
    CreateRecipeScreenContent(onNavigationClick = {
        navController.popBackStack()
    }, viewModel = createRecipeViewModel)
}

@Composable
fun CreateRecipeScreenContent(
    onNavigationClick: (() -> Unit)? = null,
    viewModel: CreateRecipeViewModel
) {
    EdgeToEdgeScaffold(
        topBar = {
            TopAppBar(navigationIcon = {
                IconButton(onClick = { onNavigationClick?.invoke() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = null)
                }
            }, title = {})
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .padding(horizontal = 20.dp)
                .padding(bottom = 16.dp)
        ) {
            createRecipeTitle()
            recipeImage(
                modifier = Modifier
                    .aspectRatio(16f / 9f)
                    .clip(RoundedCornerShape10Percent)
            )
            item {
                RecipeTextField(
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .requiredHeight(56.dp),
                    hintText = stringResource(R.string.please_type_recipe_name),
                    value = viewModel.recipeName.collectAsStateWithLifecycle().value,
                    onValueChange = { viewModel.updateRecipeName(it) },
                    isErrorEnabled = !viewModel.recipeNameValidationResult.collectAsStateWithLifecycle().value.isSuccess,
                    errorMessage = viewModel.recipeNameValidationResult.collectAsStateWithLifecycle().value.errorMessage
                )

                RecipeTextField(
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .requiredHeight(56.dp),
                    hintText = stringResource(R.string.please_type_recipe_description),
                    value = viewModel.recipeDescription.collectAsStateWithLifecycle().value,
                    onValueChange = { viewModel.updateRecipeDescription(it) },
                    isErrorEnabled = !viewModel.recipeDescriptionValidationResult.collectAsStateWithLifecycle().value.isSuccess,
                    errorMessage = viewModel.recipeDescriptionValidationResult.collectAsStateWithLifecycle().value.errorMessage
                )
            }
        }
    }
}

private fun LazyListScope.createRecipeTitle() {
    item {
        Text(
            text = stringResource(id = R.string.create_recipe),
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp
            )
        )
    }
}

private fun LazyListScope.recipeImage(modifier: Modifier = Modifier) {
    item {
        SubcomposeAsyncImage(
            model = "https://www.allrecipes.com/thmb/VjVrkCVYaalH2JXogJMoFQ_a-zI=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/7016-french-toast-mfs-010-0e1007bf0b47433abe91f2f0c74e5a27.jpg",
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier,
            loading = {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(24.dp)
                        .align(
                            Alignment.Center
                        )
                )
            }
        )
    }
}
