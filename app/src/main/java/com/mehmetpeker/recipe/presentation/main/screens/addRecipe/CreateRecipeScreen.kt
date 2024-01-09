@file:OptIn(ExperimentalMaterial3Api::class)

package com.mehmetpeker.recipe.presentation.main.screens.addRecipe

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.mehmetpeker.recipe.R
import com.mehmetpeker.recipe.base.EdgeToEdgeScaffold
import com.mehmetpeker.recipe.common.RecipeInputDialog
import com.mehmetpeker.recipe.common.RecipeInputDialogProperties
import com.mehmetpeker.recipe.common.RecipeOutlinedButton
import com.mehmetpeker.recipe.common.RecipeRoundedButton
import com.mehmetpeker.recipe.designsystem.IngredientTextFieldType
import com.mehmetpeker.recipe.designsystem.IngredientsTextField
import com.mehmetpeker.recipe.designsystem.RecipeTextField
import com.mehmetpeker.recipe.designsystem.theme.RecipeFontFamily
import com.mehmetpeker.recipe.designsystem.theme.RecipeTheme
import com.mehmetpeker.recipe.designsystem.theme.RoundedCornerShape10Percent
import com.mehmetpeker.recipe.designsystem.theme.md_theme_light_primary
import com.mehmetpeker.recipe.presentation.main.screens.addRecipe.components.CreateRecipeInput
import com.mehmetpeker.recipe.presentation.main.screens.addRecipe.uiModel.CategoriesUiModel
import com.mehmetpeker.recipe.presentation.main.screens.addRecipe.uiModel.MaterialsUiModel
import com.mehmetpeker.recipe.util.extension.horizontalSpace
import com.mehmetpeker.recipe.util.extension.scaledSp
import com.mehmetpeker.recipe.util.extension.toFile
import com.mehmetpeker.recipe.util.extension.verticalSpace
import org.koin.androidx.compose.koinViewModel

@Composable
fun CreateRecipeScreen(
    navController: NavController,
    createRecipeViewModel: CreateRecipeViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            val selectedFile = uri?.toFile(context.contentResolver)
            createRecipeViewModel.onImageSelected(selectedFile)

        }
    CreateRecipeScreenContent(onNavigationClick = {
        navController.popBackStack()
    }, viewModel = createRecipeViewModel, onAddClick = {
        launcher.launch(
            PickVisualMediaRequest(
                mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
            )
        )
    })
}

@Composable
fun CreateRecipeScreenContent(
    onNavigationClick: () -> Unit = {},
    onAddClick: () -> Unit = {},
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
                .padding(horizontal = 24.dp)
                .padding(bottom = 8.dp)
                .fillMaxWidth()
                .navigationBarsPadding()
                .heightIn(min = 56.dp)
                .then(
                    if (viewModel.buttonVisibility) Modifier else Modifier.alpha(0.5F)

                ), onClick = { viewModel.createRecipe() }
            )
            {
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
            item {
                RecipeImage(
                    viewModel = viewModel,
                    onRetryClick = {
                        viewModel.onImageUpload()
                    },
                    onAddClick = onAddClick
                )
            }
            item {
                RecipeTextField(
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .heightIn(min = 56.dp),
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
                        .heightIn(min = 56.dp),
                    hintText = stringResource(R.string.please_type_recipe_description),
                    value = viewModel.recipeDescription.collectAsStateWithLifecycle().value,
                    onValueChange = { viewModel.updateRecipeDescription(it) },
                    isErrorEnabled = !viewModel.recipeDescriptionValidationResult.collectAsStateWithLifecycle().value.isSuccess,
                    errorMessage = viewModel.recipeDescriptionValidationResult.collectAsStateWithLifecycle().value.errorMessage
                )
            }
            item {
                RecipeCategory(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .height(60.dp),
                    categoryList = viewModel.categories.collectAsStateWithLifecycle().value,
                    selectedCategory = viewModel.selectedCategory.collectAsStateWithLifecycle().value,
                    onCategorySelected = {
                        viewModel.updateSelectedCategory(it)
                    }
                )
            }
            item {
                PreparationTime(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .height(60.dp),
                    isErrorEnabled = !viewModel.recipePreparationTimeValidationResult.collectAsStateWithLifecycle().value.isSuccess,
                    errorMessage = viewModel.recipePreparationTimeValidationResult.collectAsStateWithLifecycle().value.errorMessage
                        ?: emptyList(),
                    value = viewModel.preparationTime.collectAsStateWithLifecycle().value,
                    onValueChange = { newValue -> viewModel.updatePreparationTime(newValue) }
                )
            }
            item {
                CookTime(
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .height(60.dp),
                    isErrorEnabled = !viewModel.recipeCookingTimeValidationResult.collectAsStateWithLifecycle().value.isSuccess,
                    errorMessage = viewModel.recipeCookingTimeValidationResult.collectAsStateWithLifecycle().value.errorMessage
                        ?: emptyList(),
                    value = viewModel.cookTime.collectAsStateWithLifecycle().value,
                    onValueChange = { newValue -> viewModel.updateCookTime(newValue) }
                )
            }
            item {
                ServesTime(
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .height(60.dp),
                    isErrorEnabled = !viewModel.recipeServeValidationResult.collectAsStateWithLifecycle().value.isSuccess,
                    errorMessage = viewModel.recipeServeValidationResult.collectAsStateWithLifecycle().value.errorMessage
                        ?: emptyList(),
                    value = viewModel.servesAmount.collectAsStateWithLifecycle().value,
                    onValueChange = { newValue -> viewModel.updateServes(newValue) }
                )
            }
            item {
                Materials(
                    Modifier
                        .fillMaxWidth(),
                    viewModel.ingredients.value,
                    viewModel.materials.collectAsStateWithLifecycle().value,
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

@Composable
private fun RecipeCategory(
    modifier: Modifier = Modifier,
    categoryList: List<CategoriesUiModel> = emptyList(),
    selectedCategory: CategoriesUiModel? = null,
    onCategorySelected: (CategoriesUiModel) -> Unit = {}
) {
    var isDialogExpanded by remember {
        mutableStateOf(false)
    }
    CreateRecipeInput(
        modifier = modifier
            .clickable {
                isDialogExpanded = true
            }, icon = {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(color = Color.White, shape = RoundedCornerShape(10.dp))
                    .padding(8.dp)
            ) {
                Icon(
                    Icons.Default.Menu,
                    contentDescription = null,
                    tint = Color.Red
                )
            }
        }, title = stringResource(id = R.string.category), value = selectedCategory?.name ?: "",
        onAction = {
            isDialogExpanded = true
        }
    )
    if (isDialogExpanded) {
        Dialog(
            onDismissRequest = { isDialogExpanded = false },
        ) {
            RecipeTheme {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                ) {
                    LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
                        itemsIndexed(categoryList) { index, item ->
                            Text(
                                text = item.name ?: "",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        onCategorySelected(item)
                                        isDialogExpanded = false
                                    },
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Normal
                                )
                            )
                            if (index < categoryList.lastIndex) {
                                Divider(modifier = Modifier.padding(horizontal = 16.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ServesTime(
    modifier: Modifier = Modifier,
    isErrorEnabled: Boolean = false,
    errorMessage: List<String> = emptyList(),
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    onConfirm: (value: String) -> Unit = {}
) {
    var isDialogVisible by remember {
        mutableStateOf(false)
    }
    CreateRecipeInput(
        modifier = modifier
            .clickable {
                isDialogVisible = true
            }, icon = {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(color = Color.White, shape = RoundedCornerShape(10.dp))
                    .padding(8.dp)
            ) {
                Icon(
                    painterResource(id = R.drawable.profile),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            }
        }, title = stringResource(id = R.string.serves_amount), value = value.text,
        onAction = {
            isDialogVisible = true
        }
    )
    if (isDialogVisible) {
        RecipeInputDialog(
            onDismissRequest = { isDialogVisible = false },
            onConfirm = {
                if (!isErrorEnabled) {
                    isDialogVisible = false
                }
                onConfirm(it)
            },
            isErrorEnabled = isErrorEnabled,
            errorMessage = errorMessage,
            properties = RecipeInputDialogProperties(
                title = stringResource(id = R.string.serves_amount),
                placeHolder = stringResource(id = R.string.please_type_serves_amount),
            ),
            value = value,
            onValueChange = onValueChange
        )
    }
}

@Composable
private fun CookTime(
    modifier: Modifier = Modifier,
    isErrorEnabled: Boolean = false,
    errorMessage: List<String> = emptyList(),
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    onConfirm: (value: String) -> Unit = {}
) {
    var isDialogVisible by remember {
        mutableStateOf(false)
    }
    CreateRecipeInput(
        modifier = modifier
            .clickable {
                isDialogVisible = true
            }, icon = {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(color = Color.White, shape = RoundedCornerShape(10.dp))
                    .padding(8.dp)
            ) {
                Icon(
                    painterResource(id = R.drawable.clock),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            }
        }, title = stringResource(id = R.string.cook_time), value = value.text,
        onAction = {
            isDialogVisible = true
        }
    )
    if (isDialogVisible) {
        RecipeInputDialog(
            onDismissRequest = { isDialogVisible = false },
            onConfirm = {
                if (!isErrorEnabled) {
                    isDialogVisible = false
                }
                onConfirm(it)
            },
            isErrorEnabled = isErrorEnabled,
            errorMessage = errorMessage,
            properties = RecipeInputDialogProperties(
                title = stringResource(id = R.string.cook_time),
                placeHolder = stringResource(id = R.string.please_type_cook_time),
            ),
            value = value,
            onValueChange = onValueChange
        )
    }
}

@Composable
private fun PreparationTime(
    modifier: Modifier = Modifier,
    isErrorEnabled: Boolean = false,
    errorMessage: List<String> = emptyList(),
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    onConfirm: (value: String) -> Unit = {}
) {
    var isDialogVisible by remember {
        mutableStateOf(false)
    }
    CreateRecipeInput(
        modifier = modifier
            .clickable {
                isDialogVisible = true
            }, icon = {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(color = Color.White, shape = RoundedCornerShape(10.dp))
                    .padding(8.dp)
            ) {
                Icon(
                    painterResource(id = R.drawable.clock),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            }
        }, title = stringResource(id = R.string.preparation_time), value = value.text,
        onAction = {
            isDialogVisible = true
        }
    )
    if (isDialogVisible) {
        RecipeInputDialog(
            onDismissRequest = { isDialogVisible = false },
            onConfirm = {
                if (!isErrorEnabled) {
                    isDialogVisible = false
                }
                onConfirm(it)
            },
            isErrorEnabled = isErrorEnabled,
            errorMessage = errorMessage,
            properties = RecipeInputDialogProperties(
                title = stringResource(id = R.string.preparation_time),
                placeHolder = stringResource(id = R.string.please_type_preparation_time),
            ),
            value = value,
            onValueChange = onValueChange
        )
    }
}

@Composable
private fun RecipeImage(
    modifier: Modifier = Modifier,
    viewModel: CreateRecipeViewModel,
    onAddClick: () -> Unit = {},
    onRetryClick: () -> Unit = {},

    ) {
    when {
        viewModel.selectedFile == null -> {
            EmptyImage(
                modifier = Modifier
                    .aspectRatio(1.675f)
                    .clip(RoundedCornerShape10Percent)
                    .background(Color(0xffF1F1F1)),
                onAddClick = onAddClick
            )
        }

        viewModel.imageUploadStatus == CreateRecipeViewModel.ImageUploadStatus.LOADING -> {
            Box(
                modifier = Modifier
                    .aspectRatio(1.675f)
                    .clip(RoundedCornerShape10Percent)
                    .background(Color(0xffF1F1F1)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp))
            }
        }

        viewModel.imageUploadStatus == CreateRecipeViewModel.ImageUploadStatus.FAILED -> {
            Box(
                modifier = Modifier
                    .aspectRatio(1.675f)
                    .clip(RoundedCornerShape10Percent)
                    .background(Color(0xffF1F1F1)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.recipe_image_upload_failed),
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center
                    )
                    10.verticalSpace()
                    RecipeRoundedButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 56.dp), onClick = onRetryClick
                    ) {
                        Text(
                            text = stringResource(id = R.string.try_again).uppercase(),
                            fontFamily = RecipeFontFamily.poppinsFamily,
                            fontWeight = FontWeight.Medium,
                            fontSize = 15.scaledSp,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }

        viewModel.imageUploadStatus == CreateRecipeViewModel.ImageUploadStatus.SUCCESS -> {
            SubcomposeAsyncImage(
                model = viewModel.uploadedImageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .aspectRatio(1.675f)
                    .clip(RoundedCornerShape10Percent),
                loading = {
                    Box(
                        modifier = Modifier.size(24.dp), contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(modifier = Modifier.size(24.dp))
                    }
                }
            )
        }
    }

}


@Composable
private fun EmptyImage(
    modifier: Modifier = Modifier,
    onAddClick: () -> Unit = {}
) {
    Box(modifier = modifier) {
        IconButton(
            modifier = Modifier
                .align(Alignment.Center),
            onClick = onAddClick
        ) {
            Icon(
                Icons.Default.AddCircle,
                contentDescription = null,
                tint = md_theme_light_primary,
                modifier = Modifier.size(56.dp)
            )
        }
    }
}

@Composable
private fun Materials(
    modifier: Modifier = Modifier,
    ingredients: List<MaterialsUiModel>,
    materialList: List<MaterialsUiModel>,
    onAddIngredients: () -> Unit,
    onIngredientChanged: (index: Int, material: MaterialsUiModel) -> Unit,
    onRemove: (index: Int) -> Unit
) {
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
            material = ingredient,
            materialList = materialList,
            index = index,
            onIngredientChanged = onIngredientChanged,
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


@Composable
private fun MeasurementItem(
    modifier: Modifier = Modifier,
    index: Int,
    materialList: List<MaterialsUiModel> = emptyList(),
    material: MaterialsUiModel,
    onIngredientChanged: (index: Int, material: MaterialsUiModel) -> Unit,
    onRemove: (index: Int) -> Unit
) {
    var isDialogExpanded by remember {
        mutableStateOf(false)
    }
    Row(
        modifier = modifier
            .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
    ) {
        IngredientsTextField(
            modifier = Modifier
                .padding(top = 12.dp)
                .fillMaxWidth()
                .weight(1f)
                .heightIn(min = 56.dp),
            placeHolder = stringResource(R.string.ingredients_name),
            value = TextFieldValue(
                material.name ?: "-",
                selection = TextRange(material.name?.length ?: 0)
            ),
            onValueChange = {
            },
            readOnly = true,
            enabled = false,
            onClick = {
                isDialogExpanded = true
            }
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
                    material.amount.toString(),
                    selection = TextRange(material.amount.toString().length)
                ),
                onValueChange = {
                    val newIngredient = material.copy(amount = it.text.toIntOrNull() ?: 0)
                    onIngredientChanged(index, newIngredient)
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                type = IngredientTextFieldType.Unit(
                    listOf(
                        "Adet",
                        "Gram",
                        "Kilogram",
                        "Litre",
                        "Mililitre"
                    )
                ),
                onUnitSelected = { selectedUnit ->
                    val newIngredient = material.copy(measurement = selectedUnit)
                    onIngredientChanged(index, newIngredient)
                }
            )
            IconButton(onClick = { onRemove(index) }) {
                Icon(painterResource(id = R.drawable.minus_border), contentDescription = null)
            }
        }
    }
    if (isDialogExpanded) {
        Dialog(
            onDismissRequest = { isDialogExpanded = false },
        ) {
            RecipeTheme {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                ) {
                    LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
                        itemsIndexed(materialList) { materialIndex, item ->
                            Text(
                                text = item.name ?: "",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        val newIngredient = material.copy(name = item.name)
                                        onIngredientChanged(index, newIngredient)
                                        isDialogExpanded = false
                                    },
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Normal
                                )
                            )
                            if (index < materialList.lastIndex) {
                                Divider(modifier = Modifier.padding(horizontal = 16.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}
