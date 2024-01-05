@file:OptIn(ExperimentalMaterial3Api::class)

package com.mehmetpeker.recipe.presentation.main.screens.addRecipe

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.mehmetpeker.recipe.R
import com.mehmetpeker.recipe.base.EdgeToEdgeScaffold
import com.mehmetpeker.recipe.common.RecipeOutlinedButton
import com.mehmetpeker.recipe.data.model.Ingredients
import com.mehmetpeker.recipe.designsystem.IngredientTextFieldType
import com.mehmetpeker.recipe.designsystem.IngredientsTextField
import com.mehmetpeker.recipe.designsystem.RecipeTextField
import com.mehmetpeker.recipe.designsystem.theme.RecipeFontFamily
import com.mehmetpeker.recipe.designsystem.theme.RoundedCornerShape10Percent
import com.mehmetpeker.recipe.designsystem.theme.md_theme_light_primary
import com.mehmetpeker.recipe.util.extension.horizontalSpace
import com.mehmetpeker.recipe.util.extension.scaledSp
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
        },
        bottomBar = {
            RecipeOutlinedButton(modifier = Modifier
                .fillMaxWidth()
                .heightIn(min =56.dp), onClick = {}) {
                Text(
                    text = stringResource(id = R.string.save_recipe).uppercase(),
                    fontFamily = RecipeFontFamily.poppinsFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 15.scaledSp,
                    color = Color.White
                )
            }
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
            if (viewModel.imageUri.isNullOrEmpty()) {
                emptyImage(
                    modifier = Modifier
                        .aspectRatio(16f / 9f)
                        .clip(RoundedCornerShape10Percent)
                        .background(Color(0xffF1F1F1))
                ) {
                    viewModel.imageUri = "non empty"
                }
            } else {
                recipeImage(
                    modifier = Modifier
                        .aspectRatio(16f / 9f)
                        .clip(RoundedCornerShape10Percent)
                )
            }

            item {
                RecipeTextField(
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .heightIn(min =56.dp),
                    hintText = stringResource(R.string.please_type_recipe_name),
                    value = viewModel.recipeName.collectAsStateWithLifecycle().value,
                    onValueChange = { viewModel.updateRecipeName(it) },
                    isErrorEnabled = !viewModel.recipeNameValidationResult.collectAsStateWithLifecycle().value.isSuccess,
                    errorMessage = viewModel.recipeNameValidationResult.collectAsStateWithLifecycle().value.errorMessage
                )
            }
            item {
                RecipeTextField(
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .heightIn(min =56.dp),
                    hintText = stringResource(R.string.please_type_recipe_description),
                    value = viewModel.recipeDescription.collectAsStateWithLifecycle().value,
                    onValueChange = { viewModel.updateRecipeDescription(it) },
                    isErrorEnabled = !viewModel.recipeDescriptionValidationResult.collectAsStateWithLifecycle().value.isSuccess,
                    errorMessage = viewModel.recipeDescriptionValidationResult.collectAsStateWithLifecycle().value.errorMessage
                )
            }
            ingredients(
                Modifier
                    .fillMaxWidth(),
                viewModel.ingredients.value,
                onAddIngredients = viewModel::addIngredient,
                onIngredientChanged = { index, ingredient ->
                    viewModel.updateIngredient(index, ingredient)
                },
                onRemove = { removeIndex ->
                    viewModel.onRemove(removeIndex)
                }
            )
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

private fun LazyListScope.emptyImage(
    modifier: Modifier = Modifier,
    onAddClick: (() -> Unit)? = null
) {
    item {
        Box(modifier = modifier) {
            IconButton(
                modifier = Modifier
                    .align(Alignment.Center),
                onClick = { onAddClick?.invoke() }) {
                Icon(
                    Icons.Default.AddCircle,
                    contentDescription = null,
                    tint = md_theme_light_primary,
                    modifier = Modifier.size(56.dp)
                )
            }
        }
    }
}


private fun LazyListScope.ingredients(
    modifier: Modifier = Modifier,
    ingredients: List<Ingredients>,
    onAddIngredients: () -> Unit,
    onIngredientChanged: (index: Int, ingredient: Ingredients) -> Unit,
    onRemove: (index: Int) -> Unit
) {
    item {
        Text(
            modifier = Modifier.padding(top = 12.dp),
            text = stringResource(id = R.string.ingredients),
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.SemiBold,
                fontSize = 22.sp
            )
        )
        ingredients.forEachIndexed { index, ingredient ->
            MeasurementItem(
                modifier = modifier,
                ingredient = ingredient,
                index = index,
                onIngredientChanged = { index, ingredient ->
                    onIngredientChanged(index, ingredient)
                },
                onRemove = {
                    onRemove(it)
                }
            )
        }

        Row(modifier = Modifier
            .padding(top = 12.dp)
            .clickable {
                onAddIngredients.invoke()
            }) {
            Icon(Icons.Default.Add, contentDescription = null)
            4.horizontalSpace()
            Text(
                text = stringResource(R.string.add_new_ingredient),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}


@Composable
private fun MeasurementItem(
    modifier: Modifier = Modifier,
    index: Int,
    ingredient: Ingredients,
    onIngredientChanged: (index: Int, ingredient: Ingredients) -> Unit,
    onRemove: (index: Int) -> Unit
) {
    Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        IngredientsTextField(
            modifier = Modifier
                .padding(top = 12.dp)
                .fillMaxWidth()
                .weight(1f)
                .heightIn(min = 56.dp),
            placeHolder = stringResource(R.string.ingredients_name),
            value = TextFieldValue(ingredient.name, selection = TextRange(ingredient.name.length)),
            onValueChange = {
                val newIngredient = ingredient.copy(name = it.text)
                onIngredientChanged(index, newIngredient)
            },
        )
        12.horizontalSpace()
        Row(
            Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IngredientsTextField(
                modifier = Modifier
                    .padding(top = 12.dp)
                    .fillMaxWidth()
                    .weight(1f)
                    .heightIn(min = 56.dp),
                placeHolder = stringResource(R.string.amount),
                value = TextFieldValue(
                    ingredient.amount.toString(),
                    selection = TextRange(ingredient.amount.toString().length)
                ),
                onValueChange = {
                    val newIngredient = ingredient.copy(amount = it.text.toIntOrNull() ?: 0)
                    onIngredientChanged(index, newIngredient)
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                type = IngredientTextFieldType.Unit(listOf("gr", "lt", "kg")),
                onUnitSelected = { selectedUnit ->
                    val newIngredient = ingredient.copy(unit = selectedUnit)
                    onIngredientChanged(index, newIngredient)
                }
            )
            IconButton(onClick = { onRemove(index) }) {
                Icon(painterResource(id = R.drawable.minus_border), contentDescription = null)
            }
        }
    }
}
