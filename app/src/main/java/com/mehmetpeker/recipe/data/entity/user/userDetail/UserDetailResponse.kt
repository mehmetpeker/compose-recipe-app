package com.mehmetpeker.recipe.data.entity.user.userDetail


import com.mehmetpeker.recipe.data.entity.recipe.getRecipe.Recipe
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDetailResponse(
    @SerialName("amIFollowing")
    val amIFollowing: Boolean?,
    @SerialName("email")
    val email: String?,
    @SerialName("likedRecipes")
    val likedRecipes: List<Recipe?>?,
    @SerialName("photos")
    val photos: List<Photo?>?,
    @SerialName("profilePhoto")
    val profilePhoto: String?,
    @SerialName("recipes")
    val recipes: List<Recipe?>?,
    @SerialName("username")
    val username: String?
)

@Serializable
data class Photo(
    @SerialName("id")
    val id: Int?,
    @SerialName("url")
    val url: String?
)