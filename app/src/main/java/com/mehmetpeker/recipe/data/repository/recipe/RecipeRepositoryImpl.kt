package com.mehmetpeker.recipe.data.repository.recipe

import com.mehmetpeker.recipe.data.entity.recipe.SearchRecipeResponse
import com.mehmetpeker.recipe.domain.repository.RecipeRepository
import com.mehmetpeker.recipe.util.ApiResult
import com.mehmetpeker.recipe.util.safeRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.url
import io.ktor.http.HttpMethod

class RecipeRepositoryImpl(private val client: HttpClient) : RecipeRepository {
    override suspend fun searchRecipe(searchText: String): ApiResult<SearchRecipeResponse> {
        return client.safeRequest {
            method = HttpMethod.Get
            url("api/recipe/get-search-recipes?recipeName=$searchText")
        }
    }
}