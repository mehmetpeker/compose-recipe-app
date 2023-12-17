package com.mehmetpeker.recipe.util.validator

import android.content.Context
import com.mehmetpeker.recipe.R
import com.mehmetpeker.recipe.RecipeApplication
import com.mehmetpeker.recipe.util.ValidationResult

object TextFieldValidator {

    private val appContext: Context = RecipeApplication.getAppContext()
    private const val MIN_LENGTH = 8
    private const val MAX_LENGTH = 20

    private const val MIN_USERNAME_LENGTH = 3
    private const val MAX_USERNAME_LENGTH = 15

    fun validateUsername(username: String): ValidationResult {
        val errors = mutableListOf<String>()

        val containsOnlyValidCharacters = username.all { it.isLetterOrDigit() || it in "_-" }
        if (!containsOnlyValidCharacters) {
            errors.add(appContext.getString(R.string.error_username_invalid_characters))
        }

        val isLengthValid = username.length in MIN_USERNAME_LENGTH..MAX_USERNAME_LENGTH
        if (!isLengthValid) {
            errors.add(
                appContext.getString(
                    R.string.error_username_invalid_length,
                    MIN_USERNAME_LENGTH, MAX_USERNAME_LENGTH
                )
            )
        }

        val startsWithLetter = username.isNotEmpty() && username.first().isLetter()
        if (!startsWithLetter) {
            errors.add(appContext.getString(R.string.error_username_not_start_with_letter))
        }

        val isValid = errors.isEmpty()

        return ValidationResult(isValid, errors)
    }

    fun validatePassword(password: String): ValidationResult {
        val errors = mutableListOf<String>()

        val containsUppercase = password.any { it.isUpperCase() }
        if (!containsUppercase) {
            errors.add(appContext.getString(R.string.error_password_missing_uppercase))
        }

        val containsLowercase = password.any { it.isLowerCase() }
        if (!containsLowercase) {
            errors.add(appContext.getString(R.string.error_password_missing_lowercase))
        }

        val containsDigit = password.any { it.isDigit() }
        if (!containsDigit) {
            errors.add(appContext.getString(R.string.error_password_missing_digit))
        }

        val containsSpecialChar = password.any { !it.isLetterOrDigit() }
        if (!containsSpecialChar) {
            errors.add(appContext.getString(R.string.error_password_missing_special_char))
        }

        val isLengthValid = password.length in MIN_LENGTH..MAX_LENGTH
        if (!isLengthValid) {
            errors.add(
                appContext.getString(
                    R.string.error_password_invalid_length,
                    MIN_LENGTH, MAX_LENGTH
                )
            )
        }

        val isValid = errors.isEmpty()

        return ValidationResult(isValid, errors)
    }

    fun validateEmail(email: String): ValidationResult {
        val errors = mutableListOf<String>()

        val emailRegex = Regex("^\\S+@\\S+\\.\\S+\$")
        val isValid = emailRegex.matches(email)

        if (!isValid) {
            errors.add(appContext.getString(R.string.error_invalid_email))
        }

        return ValidationResult(isValid, errors)
    }
}
