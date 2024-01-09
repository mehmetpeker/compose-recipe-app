package com.mehmetpeker.recipe.domain.repository

import com.mehmetpeker.recipe.data.entity.recipe.SearchRecipeResponseItem
import com.mehmetpeker.recipe.data.entity.recipe.categories.GetAllCategoriesItem
import com.mehmetpeker.recipe.data.entity.recipe.categories.GetAllCategoriesResponse
import com.mehmetpeker.recipe.data.entity.recipe.createRecipe.CreateRecipeRequest
import com.mehmetpeker.recipe.data.entity.recipe.createRecipe.CreateRecipeResponse
import com.mehmetpeker.recipe.data.entity.recipe.materials.GetAllMaterialsResponse
import com.mehmetpeker.recipe.data.entity.recipe.materials.GetAllMaterialsResponseItem
import com.mehmetpeker.recipe.data.entity.recipe.uploadImage.UploadRecipeImageResponse
import com.mehmetpeker.recipe.util.ApiResult
import java.io.File

interface RecipeRepository {
    suspend fun searchRecipe(
        searchText: String
    ): ApiResult<List<SearchRecipeResponseItem>>

    suspend fun uploadRecipePhoto(file: File): ApiResult<UploadRecipeImageResponse>
    suspend fun getAllCategories(): ApiResult<List<GetAllCategoriesItem>>
    suspend fun getAllMaterials(): ApiResult<List<GetAllMaterialsResponseItem>>
    suspend fun createRecipe(createRecipeRequest: CreateRecipeRequest) : ApiResult<CreateRecipeResponse>
}