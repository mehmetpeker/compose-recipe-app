package com.mehmetpeker.recipe.presentation.main.screens.homepage

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val homeModule = module {
    viewModelOf(::HomepageViewModel)
}