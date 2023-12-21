package com.mehmetpeker.recipe.data.local.preferences

import android.content.Context

class RecipePreferences constructor(context: Context) : Preferences(context) {
    var rememberLogin by booleanPref("rememberLogin", false)
    var isUserLoggedIn by booleanPref("isUserLoggedIn", false)
    var accessToken by stringPref("accessToken", "")
    var email by stringPref("email", "")
    var userName by stringPref("userName", "")
    var profilePhotoUrl by stringPref("profilePhotoUrl", "")
}
