package com.mehmetpeker.recipe.presentation.main.screens.addRecipe

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val createRecipeModule = module {
    viewModelOf(::CreateRecipeViewModel)
}