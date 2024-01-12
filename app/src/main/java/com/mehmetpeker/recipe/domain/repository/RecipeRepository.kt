package com.mehmetpeker.recipe.domain.repository

import com.mehmetpeker.recipe.data.entity.recipe.categories.GetAllCategoriesItem
import com.mehmetpeker.recipe.data.entity.recipe.createRecipe.CreateRecipeRequest
import com.mehmetpeker.recipe.data.entity.recipe.createRecipe.CreateRecipeResponse
import com.mehmetpeker.recipe.data.entity.recipe.getAllRecipe.GetAllRecipeResponseItem
import com.mehmetpeker.recipe.data.entity.recipe.getLikedRecipe.GetLikedRecipesItem
import com.mehmetpeker.recipe.data.entity.recipe.getRecipe.GetRecipeResponse
import com.mehmetpeker.recipe.data.entity.recipe.getRecipe.Recipe
import com.mehmetpeker.recipe.data.entity.recipe.likeRecipe.LikeRecipeResponse
import com.mehmetpeker.recipe.data.entity.recipe.materials.GetAllMaterialsResponseItem
import com.mehmetpeker.recipe.data.entity.recipe.recipeComments.RecipeCommentsResponseItem
import com.mehmetpeker.recipe.data.entity.recipe.recipeComments.addComment.AddCommentRequest
import com.mehmetpeker.recipe.data.entity.recipe.recipeComments.addComment.AddCommentResponse
import com.mehmetpeker.recipe.data.entity.recipe.searchRecipeIncludedMaterials.SearchRecipeWithIncludedMaterialsRequestItem
import com.mehmetpeker.recipe.data.entity.recipe.uploadImage.UploadRecipeImageResponse
import com.mehmetpeker.recipe.util.ApiResult
import java.io.File

interface RecipeRepository {
    suspend fun searchRecipe(
        searchText: String
    ): ApiResult<List<Recipe>>

    suspend fun uploadRecipePhoto(file: File): ApiResult<UploadRecipeImageResponse>
    suspend fun getAllCategories(): ApiResult<List<GetAllCategoriesItem>>
    suspend fun getAllMaterials(): ApiResult<List<GetAllMaterialsResponseItem>>
    suspend fun createRecipe(createRecipeRequest: CreateRecipeRequest): ApiResult<CreateRecipeResponse>
    suspend fun getAllRecipes(): ApiResult<List<GetAllRecipeResponseItem>>
    suspend fun getAllRecipesByCategory(categoryId: String): ApiResult<List<GetAllRecipeResponseItem>>
    suspend fun getRecipe(recipeId: String): ApiResult<GetRecipeResponse>
    suspend fun getLikedRecipes(): ApiResult<List<GetLikedRecipesItem>>
    suspend fun likeRecipe(recipeId: String): ApiResult<LikeRecipeResponse>
    suspend fun dislikeRecipe(recipeId: String): ApiResult<LikeRecipeResponse>

    suspend fun getCommentsByRecipe(recipeId: String): ApiResult<List<RecipeCommentsResponseItem>>
    suspend fun addComment(addCommentRequest: AddCommentRequest): ApiResult<AddCommentResponse>

    suspend fun searchRecipeWithIncludedMaterials(
        searchText: String,
        searchRecipeWithExcludedMaterialsRequest: List<SearchRecipeWithIncludedMaterialsRequestItem>
    ): ApiResult<List<Recipe>>

    suspend fun searchRecipeWithExcludedMaterials(
        searchText: String,
        searchRecipeWithExcludedMaterialsRequest: List<SearchRecipeWithIncludedMaterialsRequestItem>
    ): ApiResult<List<Recipe>>
}