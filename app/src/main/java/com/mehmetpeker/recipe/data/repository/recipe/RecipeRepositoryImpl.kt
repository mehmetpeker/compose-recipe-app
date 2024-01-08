package com.mehmetpeker.recipe.data.repository.recipe

import com.mehmetpeker.recipe.data.entity.ErrorResponseBody
import com.mehmetpeker.recipe.data.entity.recipe.SearchRecipeResponseItem
import com.mehmetpeker.recipe.data.entity.recipe.uploadImage.UploadRecipeImageResponse
import com.mehmetpeker.recipe.domain.repository.RecipeRepository
import com.mehmetpeker.recipe.util.ApiError
import com.mehmetpeker.recipe.util.ApiResult
import com.mehmetpeker.recipe.util.ApiSuccess
import com.mehmetpeker.recipe.util.safeRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.url
import io.ktor.client.statement.bodyAsText
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.isSuccess
import kotlinx.serialization.json.Json
import java.io.File

class RecipeRepositoryImpl(private val client: HttpClient) : RecipeRepository {
    override suspend fun searchRecipe(searchText: String): ApiResult<List<SearchRecipeResponseItem>> {
        return client.safeRequest {
            method = HttpMethod.Get
            url("api/recipe/get-search-recipes?recipeName=$searchText")
        }
    }

    override suspend fun uploadRecipePhoto(file: File): ApiResult<UploadRecipeImageResponse> {
        return try {
            val response = client.submitFormWithBinaryData(
                url = "/api/recipe/add-photo/3",
                formData = formData {
                    append("File", file.readBytes(), Headers.build {
                        append(HttpHeaders.ContentType, "image/png")
                        append(HttpHeaders.ContentDisposition, "filename=\"recipe_image.png\"")
                    })
                }
            )
            if (response.status.isSuccess()) {
                ApiSuccess(response.body())
            } else {
                val errorResponseBody: ErrorResponseBody? = try {
                    Json.decodeFromString(response.bodyAsText())
                } catch (e: Exception) {
                    null
                }
                ApiError(
                    errorBody = errorResponseBody,
                )
            }
        } catch (e: Exception) {
            ApiError()
        }
    }
}