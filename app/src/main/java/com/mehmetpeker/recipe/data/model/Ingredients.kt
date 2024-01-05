package com.mehmetpeker.recipe.data.model

data class Ingredients(
    val name: String,
    val amount: Int,
    val unit: String
) {
    fun isValid(): Boolean = this.name.isNotEmpty() && amount > 0 && unit.isNotEmpty()
}
