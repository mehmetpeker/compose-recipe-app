package com.mehmetpeker.recipe.presentation.authentication.resetPassword

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val resetPasswordModule = module {
    viewModel { ResetPasswordViewModel(get(), get()) }
}