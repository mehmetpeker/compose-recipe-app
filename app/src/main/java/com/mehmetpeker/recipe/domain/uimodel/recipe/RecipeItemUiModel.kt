package com.mehmetpeker.recipe.domain.uimodel.recipe

import com.mehmetpeker.recipe.data.entity.recipe.getAllRecipe.GetAllRecipeResponseItem

data class RecipeItemUiModel(
    val recipeId: String,
    val recipePhotoUrl: String,
    val recipeName: String
)

fun GetAllRecipeResponseItem.toRecipeItemUiModel(): RecipeItemUiModel {
    return RecipeItemUiModel(
        recipeId = id?.toString() ?: "",
        recipePhotoUrl = photoUrl ?: "",
        recipeName = name ?: ""
    )
}