package com.mehmetpeker.recipe.data.entity.recipe

import kotlinx.serialization.Serializable

@Serializable
class SearchRecipeResponse : ArrayList<SearchRecipeResponseItem>()

@Serializable
class Materials : ArrayList<Any>()