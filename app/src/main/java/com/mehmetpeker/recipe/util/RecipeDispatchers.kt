package com.mehmetpeker.recipe.util

import kotlinx.coroutines.Dispatchers

object RecipeDispatchers {
    val io = Dispatchers.IO
    val main = Dispatchers.Main
    val default = Dispatchers.Default
}