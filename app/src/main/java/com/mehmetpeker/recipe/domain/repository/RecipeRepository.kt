package com.mehmetpeker.recipe.domain.repository

import com.mehmetpeker.recipe.data.entity.recipe.SearchRecipeResponseItem
import com.mehmetpeker.recipe.data.entity.recipe.uploadImage.UploadRecipeImageResponse
import com.mehmetpeker.recipe.util.ApiResult
import java.io.File

interface RecipeRepository {
    suspend fun searchRecipe(
        searchText: String
    ): ApiResult<List<SearchRecipeResponseItem>>

    suspend fun uploadRecipePhoto(file: File): ApiResult<UploadRecipeImageResponse>
}