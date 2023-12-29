@file:OptIn(ExperimentalMaterial3Api::class)

package com.mehmetpeker.recipe.presentation.main.screens.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mehmetpeker.recipe.R
import com.mehmetpeker.recipe.base.BaseScreen
import com.mehmetpeker.recipe.common.SearchBar
import com.mehmetpeker.recipe.data.entity.recipe.SearchRecipeResponse
import com.mehmetpeker.recipe.util.extension.verticalSpace
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchScreen(navController: NavController, searchViewModel: SearchViewModel = koinViewModel()) {
    val uiState by searchViewModel.searchUiState.collectAsState()
    BaseScreen(viewModel = searchViewModel, navController = navController) {
        SearchScreenContent(uiState = uiState, onSearchClick = {
            searchViewModel.searchRecipe(it)
        }, onSearchTextFieldValueChange = {
            searchViewModel.changeTextFieldValue(it)
        }, searchTextFieldValue = searchViewModel.searchTextFieldValue.value) {
            navController.popBackStack()
        }
    }
}

@Composable
fun SearchScreenContent(
    uiState: SearchViewModel.SearchUiState,
    searchTextFieldValue: TextFieldValue,
    onSearchTextFieldValueChange: ((textFieldValue: TextFieldValue) -> Unit)? = null,
    onSearchClick: ((searchText: String) -> Unit)? = null,
    onNavigationClick: (() -> Unit)? = null
) {

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(onClick = { onNavigationClick?.invoke() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                }, title = { Text(stringResource(id = R.string.title_search)) })
        }
    ) {
        Column(modifier = Modifier.padding(it).fillMaxSize()) {
            SearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
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
            when (uiState) {
                is SearchViewModel.SearchUiState.Idle -> Box(modifier = Modifier.fillMaxSize())
                is SearchViewModel.SearchUiState.NotFound -> EmptySearchScreenContent(modifier = Modifier.fillMaxSize())
                is SearchViewModel.SearchUiState.RecipeFound -> {
                    SearchScreenSuccessContent(
                        Modifier.fillMaxSize(),
                        searchRecipeResponse = uiState.list
                    )
                }
            }
        }
    }
}

@Composable
fun SearchScreenSuccessContent(modifier: Modifier, searchRecipeResponse: SearchRecipeResponse) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LazyColumn {
            items(searchRecipeResponse) {
                Text(text = it.name ?: "")
            }
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