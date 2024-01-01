package com.mehmetpeker.recipe.presentation.main.screens.recipeDetail

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val recipeDetailModule = module {
    viewModelOf(::RecipeDetailViewModel)
}