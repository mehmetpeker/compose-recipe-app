package com.mehmetpeker.recipe.presentation.main.screens.userDetail

import com.mehmetpeker.recipe.base.BaseViewModel
import com.mehmetpeker.recipe.data.UserRepositoryImpl
import com.mehmetpeker.recipe.util.RecipeDispatchers

class UserDetailViewModel(
    private val recipeDispatchers : RecipeDispatchers,
    private val userRepositoryImpl: UserRepositoryImpl
) : BaseViewModel() {
}