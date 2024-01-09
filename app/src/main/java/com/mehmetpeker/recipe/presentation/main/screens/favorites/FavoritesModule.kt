package com.mehmetpeker.recipe.presentation.main.screens.favorites

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val favoritesModule = module {
    viewModelOf(::FavoritesViewModel)
}