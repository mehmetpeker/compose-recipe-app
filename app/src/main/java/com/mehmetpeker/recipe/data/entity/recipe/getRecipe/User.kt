package com.mehmetpeker.recipe.data.entity.recipe.getRecipe


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName("amIFollowing")
    val amIFollowing: Boolean?,
    @SerialName("comments")
    val comments: List<String>?,
    @SerialName("email")
    val email: String?,
    @SerialName("likedRecipes")
    val likedRecipes: List<LikedRecipe>?,
    @SerialName("photos")
    val photos: List<String>?,
    @SerialName("profilePhoto")
    val profilePhoto: List<String>?,
    @SerialName("recipes")
    val recipes: List<Recipe>?,
    @SerialName("username")
    val username: String?
)