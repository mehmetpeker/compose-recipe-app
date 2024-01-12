package com.mehmetpeker.recipe.presentation.authentication.updatePassword

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val updatePasswordModule = module {
    viewModelOf(::UpdatePasswordViewModel)
}