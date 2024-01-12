@file:OptIn(ExperimentalMaterial3Api::class)

package com.mehmetpeker.recipe.presentation.main.screens.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.mehmetpeker.recipe.R
import com.mehmetpeker.recipe.base.BaseScreen
import com.mehmetpeker.recipe.base.EdgeToEdgeScaffold
import com.mehmetpeker.recipe.common.SearchBar
import com.mehmetpeker.recipe.data.entity.recipe.SearchRecipeResponseItem
import com.mehmetpeker.recipe.designsystem.theme.RecipeTheme
import com.mehmetpeker.recipe.designsystem.theme.md_theme_light_primary
import com.mehmetpeker.recipe.presentation.main.screens.addRecipe.uiModel.MaterialsUiModel
import com.mehmetpeker.recipe.util.NavArgumentConstants
import com.mehmetpeker.recipe.util.RouteConstants
import com.mehmetpeker.recipe.util.extension.horizontalSpace
import com.mehmetpeker.recipe.util.extension.verticalSpace
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchScreen(
    mainNavController: NavController,
    navController: NavController,
    searchViewModel: SearchViewModel = koinViewModel()
) {
    val uiState by searchViewModel.searchUiState.collectAsState()
    val radioOptions = listOf("Malzemeleri içersin", "Malzemeleri içermesin")
    var selectedOption by remember { mutableStateOf(radioOptions[0]) }
    val materials by searchViewModel.materials.collectAsStateWithLifecycle()
    val selectedMaterials by searchViewModel.selectedMaterials.collectAsStateWithLifecycle()
    BaseScreen(viewModel = searchViewModel, navController = navController) {
        SearchScreenContent(
            uiState = uiState,
            onSearchClick = {
                searchViewModel.searchRecipe(it)
            },
            onSearchTextFieldValueChange = {
                searchViewModel.changeTextFieldValue(it)
            },
            searchTextFieldValue = searchViewModel.searchTextFieldValue.value,
            onNavigationClick = {
                navController.popBackStack()
            },
            onRecipeDetailClick = {
                mainNavController.navigate(
                    RouteConstants.ROUTE_RECIPE_DETAIL.replace(
                        "{${NavArgumentConstants.RECIPE_ID}}",
                        it.id.toString()
                    )
                )
            },
            filterOptions = radioOptions,
            selectedOption = selectedOption,
            onClick = {
                selectedOption = it
            },
            materials = materials,
            selectedMaterials = selectedMaterials,
            onAddMaterialClick = {
                searchViewModel.addMaterialToFilter(it)
            },
            onRemoveMaterialClick = {
                searchViewModel.removeMaterialToFilter(it)
            }
        )
    }
}

@Composable
fun SearchScreenContent(
    uiState: SearchViewModel.SearchUiState,
    searchTextFieldValue: TextFieldValue,
    onSearchTextFieldValueChange: ((textFieldValue: TextFieldValue) -> Unit)? = null,
    onSearchClick: ((searchText: String) -> Unit)? = null,
    onNavigationClick: (() -> Unit)? = null,
    onRecipeDetailClick: ((SearchRecipeResponseItem) -> Unit)? = null,
    filterOptions: List<String>,
    selectedOption: String,
    onClick: ((String) -> Unit)?,
    selectedMaterials: List<MaterialsUiModel>,
    materials: List<MaterialsUiModel>,
    onAddMaterialClick: (MaterialsUiModel) -> Unit = {},
    onRemoveMaterialClick: (MaterialsUiModel) -> Unit = {},
) {
    var isDialogExpanded by remember {
        mutableStateOf(false)
    }
    EdgeToEdgeScaffold(
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(onClick = { onNavigationClick?.invoke() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                }, title = { Text(stringResource(id = R.string.title_search)) })
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(vertical = 8.dp, horizontal = 24.dp)
                .fillMaxSize()
        ) {
            SearchBar(
                modifier = Modifier
                    .fillMaxWidth(),
                value = searchTextFieldValue,
                onValueChange = { newTextFieldValue ->
                    onSearchTextFieldValueChange?.invoke(newTextFieldValue)
                },
                onClearClick = {
                    onSearchTextFieldValueChange?.invoke(TextFieldValue(""))
                }, onSearchClick = { searchText ->
                    onSearchClick?.invoke(searchText)
                }
            )
            4.verticalSpace()
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Malzemeye göre filtreleyebilirsiniz",
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    maxLines = 3
                )
                TextButton(onClick = { isDialogExpanded = true }) {
                    Text(text = "Malzeme Seç", style = MaterialTheme.typography.titleMedium)
                }
            }
            if (selectedMaterials.isNotEmpty()) {
                Text(text = "Malzemeye Göre Filtrele", style = MaterialTheme.typography.titleMedium)
                filterOptions.forEach { filterName ->
                    Row(verticalAlignment = CenterVertically) {
                        RadioButton(
                            selected = (filterName == selectedOption),
                            onClick = { onClick?.invoke(filterName) }
                        )
                        Text(
                            text = filterName,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }
                Row(Modifier.fillMaxWidth()) {
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        horizontalArrangement = Arrangement.spacedBy(space = 4.dp)
                    ) {
                        items(selectedMaterials) {
                            Row(
                                Modifier
                                    .border(
                                        1.dp, md_theme_light_primary,
                                        CircleShape
                                    )
                                    .padding(horizontal = 4.dp),
                                verticalAlignment = CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = it.name ?: "-",
                                    style = MaterialTheme.typography.labelMedium
                                )
                                2.horizontalSpace()
                                IconButton(onClick = { onRemoveMaterialClick(it) }) {
                                    Icon(Icons.Default.Clear, contentDescription = null)
                                }
                            }
                        }
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
                                itemsIndexed(materials) { materialIndex, item ->
                                    Text(
                                        text = item.name ?: "",
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                onAddMaterialClick(item)
                                                isDialogExpanded = false
                                            },
                                        textAlign = TextAlign.Center,
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontWeight = FontWeight.Normal
                                        )
                                    )
                                    if (materialIndex < materials.lastIndex) {
                                        Divider(modifier = Modifier.padding(horizontal = 16.dp))
                                    }
                                }
                            }
                        }
                    }
                }
            }
            when (uiState) {
                is SearchViewModel.SearchUiState.Idle -> Box(modifier = Modifier.fillMaxSize())
                is SearchViewModel.SearchUiState.NotFound -> EmptySearchScreenContent(modifier = Modifier.fillMaxSize())
                is SearchViewModel.SearchUiState.RecipeFound -> {
                    SearchScreenSuccessContent(
                        Modifier.fillMaxSize(),
                        searchRecipeResponse = uiState.list
                    ) {
                        onRecipeDetailClick?.invoke(it)
                    }
                }
            }
        }
    }
}


@Composable
fun SearchScreenSuccessContent(
    modifier: Modifier,
    searchRecipeResponse: List<SearchRecipeResponseItem>,
    onRecipeDetailClick: ((SearchRecipeResponseItem) -> Unit)? = null
) {
    Column(
        modifier = modifier
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(searchRecipeResponse) {
                SearchRecipeItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    item = it
                ) {
                    onRecipeDetailClick?.invoke(it)
                }
            }
        }
    }
}

@Composable
fun SearchRecipeItem(
    modifier: Modifier,
    item: SearchRecipeResponseItem,
    onRecipeDetailClick: ((SearchRecipeResponseItem) -> Unit)? = null
) {
    Row(
        modifier = modifier
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(30)
            )
            .background(color = Color.White, RoundedCornerShape(30))
            .padding(horizontal = 8.dp, vertical = 16.dp)
    ) {
        AsyncImage(
            modifier = Modifier
                .size(width = 110.dp, height = 80.dp)
                .clip(RoundedCornerShape(30)),
            model = item.photoUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        8.horizontalSpace()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Text(
                item.name ?: "--",
                maxLines = 2,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                item.description ?: "--",
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        8.horizontalSpace()
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(Color.Black, RoundedCornerShape(30))
                .align(CenterVertically)
                .clickable { onRecipeDetailClick?.invoke(item) },
        ) {
            Icon(
                modifier = Modifier.align(Alignment.Center),
                imageVector = Icons.Rounded.ArrowForward,
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}

@Composable
fun EmptySearchScreenContent(modifier: Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier.fillMaxWidth(0.5f),
            painter = painterResource(id = R.drawable.img_no_data),
            contentDescription = null
        )
        8.verticalSpace()
        Text(
            text = stringResource(id = R.string.recipe_not_found),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )
    }
}