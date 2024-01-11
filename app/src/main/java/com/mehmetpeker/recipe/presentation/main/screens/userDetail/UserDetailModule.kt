package com.mehmetpeker.recipe.presentation.main.screens.userDetail

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val userDetailModule = module {
    viewModelOf(::UserDetailViewModel)
}