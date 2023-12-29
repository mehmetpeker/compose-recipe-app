package com.mehmetpeker.recipe.domain.repository

import com.mehmetpeker.recipe.data.entity.recipe.SearchRecipeResponseItem
import com.mehmetpeker.recipe.util.ApiResult

interface RecipeRepository {
    suspend fun searchRecipe(
        searchText: String
    ): ApiResult<List<SearchRecipeResponseItem>>
}