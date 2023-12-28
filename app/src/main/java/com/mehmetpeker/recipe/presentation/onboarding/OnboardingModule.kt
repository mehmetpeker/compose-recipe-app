package com.mehmetpeker.recipe.presentation.onboarding

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val onboardingModule = module {
    viewModelOf(::OnBoardingViewModel)
}