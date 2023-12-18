package com.mehmetpeker.recipe.presentation.authentication.login

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loginModule = module {
    viewModel { LoginViewModel(get()) }
}